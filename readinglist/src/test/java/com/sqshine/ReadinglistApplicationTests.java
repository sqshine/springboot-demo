package com.sqshine;

import com.sqshine.readinglist.domain.model.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 参照spring boot sample中的test项目来写
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReadinglistApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    //@Autowired
    //private WebApplicationContext webContext;

    //@Autowired
    //private ApplicationContext applicationContext;

    //@Before
    //public void setupMockMvc() {
    //    mockMvc = MockMvcBuilders
    //            .webAppContextSetup(webContext)
    //            .build();
    //}

/*    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ReadingListController())
                .build();
    }*/

    @Test
    public void homePage() throws Exception {
        mockMvc.perform(get("/book/reader"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", is(empty())));
    }

    @Test
    public void postBook() throws Exception {
        mockMvc.perform(post("/book/craig")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "BOOK TITLE")
                .param("author", "BOOK AUTHOR")
                .param("isbn", "1234567890")
                .param("description", "DESCRIPTION"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/book/craig"));
        Book expectedBook = new Book();
        //expectedBook.setId(1L);
        expectedBook.setReader("craig");
        expectedBook.setTitle("BOOK TITLE");
        expectedBook.setAuthor("BOOK AUTHOR");
        expectedBook.setIsbn("1234567890");
        expectedBook.setDescription("DESCRIPTION");
        mockMvc.perform(get("/book/craig"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model().attributeExists("books"))
                //.andExpect(model().attribute("books", hasSize(1)))
                .andExpect(model().attribute("books", is((notNullValue()))));
        //.andExpect(model().attribute("books", contains(samePropertyValuesAs(expectedBook))));
    }

}
