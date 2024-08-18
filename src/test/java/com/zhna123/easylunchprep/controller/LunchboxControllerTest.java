package com.zhna123.easylunchprep.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhna123.easylunchprep.entity.Lunchbox;
import com.zhna123.easylunchprep.entity.User;
import com.zhna123.easylunchprep.exception.LunchboxNotFoundException;
import com.zhna123.easylunchprep.repository.LunchboxRepository;

import com.zhna123.easylunchprep.security.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LunchboxController.class)
@Import(TestSecurityConfig.class)
public class LunchboxControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private LunchboxRepository repository;

    @Test
    @WithMockUser(username = "user")
    public void testFindLunchboxById() throws Exception {
        Lunchbox lunchbox = new Lunchbox(1L, "lunchbox 1", Collections.emptyList(), false, new User());
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(lunchbox));
        mockMvc.perform(get("/lunchboxes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("lunchbox 1"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testFindLunchboxById_NotFoundException() throws Exception {
        Mockito.when(repository.findById(20L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/lunchboxes/20"))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertInstanceOf(LunchboxNotFoundException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("lunchbox not found: id=20",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    @WithMockUser(username = "user")
    public void testDeleteLunchboxById() throws Exception {
        Lunchbox lunchbox = new Lunchbox(1L, "lunchbox 1", Collections.emptyList(), false, new User());
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(lunchbox));
        mockMvc.perform(delete("/lunchboxes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "user")
    public void testUpdateLunchboxById() throws Exception {
        Lunchbox lunchbox = new Lunchbox(1L, "lunchbox 1", Collections.emptyList(), false, new User());
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(lunchbox));

        Lunchbox updated = new Lunchbox(1L, "lunchbox 1", Collections.emptyList(), true, new User());
        Mockito.when(repository.save(any(Lunchbox.class))).thenReturn(updated);

        mockMvc.perform(put("/lunchboxes/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.favorite").value(true));
    }
}
