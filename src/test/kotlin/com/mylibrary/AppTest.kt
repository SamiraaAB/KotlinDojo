package com.mylibrary

import junit.framework.TestCase
import junit.framework.TestCase.*
import org.junit.Test

class AppTest {

    class Book {
        val title: String

        constructor(title: String) {
            this.title = title
        }



        fun getPrice(): Double {
            return 8.0
        }


    }

    class Basket {

        private val books : MutableList<AppTest.Book> = ArrayList()

        fun getPrice():Double {
            val groupBy: Map<String, List<Book>> = books
                    .groupBy(Book::title)

            val discount : Double = when(groupBy.size) {
                in 0..1 -> 0.0
                   2 ->5.0
                else -> 0.0
            }
            return books.stream().mapToDouble(Book::getPrice).sum() * (1- discount/100)
        }
        fun add(book: AppTest.Book) {
            books.add(book)
        }


    }

    @Test
    fun should_buy_one_book() {
        val basket = Basket()
        basket.add(Book("un book pas cool"))
        val result = basket.getPrice()

        assertEquals(result, 8.0)
    }

    @Test
    fun should_buy_empty_basket() {
        val basket = Basket()
        val result = basket.getPrice()
        assertEquals(result, 0.0)
    }

    @Test
    fun buy_two_different_books_should_apply_five_percent_discount() {
        val basket = Basket()
        basket.add(Book("book1"))
        basket.add(Book("book2"))

        assertEquals(basket.getPrice(), 15.2)
    }
}

