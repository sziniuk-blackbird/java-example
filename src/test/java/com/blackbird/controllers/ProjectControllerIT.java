package com.blackbird.controllers;

import com.blackbird.DefaultIT;
import com.blackbird.dto.ProjectDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProjectControllerIT extends DefaultIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper jsonMapper;

    @Test
    void testCRUD() throws Exception {
        //Create project
        String model = "{\"name\":\"testProject\",\"description\":\"This is the test project\",\"status\":\"ACTIVE\"}";
        MvcResult createResult = mockMvc.perform(post("/project")
                .contentType(MediaType.APPLICATION_JSON)
                .content(model))
                .andExpect(status().isCreated())
                .andReturn();
        ProjectDto createdProject = jsonMapper.readValue(createResult.getResponse().getContentAsString(), ProjectDto.class);

        //Get project by id from previous response
        MvcResult getResult = mockMvc.perform(get("/project/" + createdProject.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        ProjectDto foundProj = jsonMapper.readValue(getResult.getResponse().getContentAsString(), ProjectDto.class);

        //Compare responses from /create and /get responses
        Assertions.assertEquals(createdProject, foundProj);

        //Update name and status of created project
        String newModel = "{\"id\":\"" + foundProj.getId() + "\",\"name\":\"new name\", \"status\":\"DISABLED\"}";
        MvcResult updateResult = mockMvc.perform(put("/project")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newModel))
                .andExpect(status().isOk())
                .andReturn();
        ProjectDto updatedProject = jsonMapper.readValue(updateResult.getResponse().getContentAsString(), ProjectDto.class);

        //Get all projects from db
        MvcResult getAllResult = mockMvc.perform(get("/project"))
                .andExpect(status().isOk())
                .andReturn();
        List<ProjectDto> projects = jsonMapper.readerFor(new TypeReference<List<ProjectDto>>() {}).readValue(getAllResult.getResponse().getContentAsString());
        List<ProjectDto> filteredProjects = projects.stream().filter(acc -> acc.equals(updatedProject)).collect(Collectors.toList());

        //Compare updated project and filtered by id response from /get all
        Assertions.assertFalse(filteredProjects.isEmpty());
        Assertions.assertEquals(updatedProject, filteredProjects.get(0));

        //Delete project by id
        mockMvc.perform(delete("/project/" + createdProject.getId()))
                .andExpect(status().isOk())
                .andReturn();

        //Try to get project by deleted id. Should return 404
        mockMvc.perform(get("/project/" + createdProject.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void testCreateNegative() throws Exception {
        String model = "{\"key1\":\"value1\",\"key2\":\"value2\"}";
        mockMvc.perform(post("/project")
                .contentType(MediaType.APPLICATION_JSON)
                .content(model))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testUpdateNegative() throws Exception {
        String model = "{\"name\":\"testProject\",\"description\":\"This is the test project\"}";
        mockMvc.perform(put("/project")
                .contentType(MediaType.APPLICATION_JSON)
                .content(model))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testDeleteNegative() throws Exception {
        mockMvc.perform(delete("/project/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
