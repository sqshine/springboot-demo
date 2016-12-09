package com.sqshine.readinglist.web;

import com.sqshine.readinglist.domain.dao.ReadingListRepository;
import com.sqshine.readinglist.domain.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/book")
public class ReadingListController {

    //为了简便，这里直接使用了dao层，实际开发中需要一个service层。
    @Autowired
    private ReadingListRepository readingListRepository;

    @GetMapping("/{reader}")
    public String readersBooks(@PathVariable("reader") String reader, Model model) {

        List<Book> bookList = readingListRepository.findByReader(reader);

        if (bookList != null) {
            model.addAttribute("books", bookList);
        }
        return "readingList";
    }

    @PostMapping(value = "/{reader}")
    public String addToReadingList(@PathVariable("reader") String reader, Book book) {
        book.setReader(reader);
        readingListRepository.save(book);
        return "redirect:/book/{reader}";
    }
}
