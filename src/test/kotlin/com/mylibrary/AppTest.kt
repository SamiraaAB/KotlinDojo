package com.mylibrary

import junit.framework.TestCase.assertEquals
import org.junit.Test

class AppTest {

    data class Book(val title: String, val price:Double = 8.0, val discounts:List<Double> = emptyList()){
        fun addDiscount(discount:Double): Book = copy(discounts=discounts.plus(discount))
        fun effectifPrice() : Double = discounts.fold(price,{acc,discount -> acc * (1 - discount / 100)})

    }

    data class Basket(val books: List<Book> = emptyList()) {

        fun getPrice():Double  =  when(differentBooksCount) {
            2 ->computeDiscount(5.0)
            3 ->computeDiscount(10.0)
            else -> computeDiscount(0.0)
        }


        private fun computeDiscount(discount: Double) = books.map(Book::price).sum() * (1 - discount / 100)

        val differentBooksCount = books
                .groupBy(Book::title)
                .size

        fun add(book: AppTest.Book) =  Basket(books.plus(book))



    }

    @Test
    fun should_buy_one_book() {
        val basket = Basket()
        assertEquals(basket.add(Book("un book pas cool")).getPrice(), 8.0)
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
        val loadedBasket = basket.add(Book("book1")).add(Book("book2"))

        assertEquals(loadedBasket.getPrice(), 15.2)
    }

    @Test
    fun buy_three_different_books_should_apply_ten_percent_discount() {
        val loadedBasket = Basket().add(Book("book1")).add(Book("book2")).add(Book("book3"))

        assertEquals(loadedBasket.getPrice(), 21.6)
    }

    @Test
    fun buy_three_different_books_and_one_identical_should_apply_ten_percent_discount() {
        val loadedBasket = Basket().add(Book("book1"))
                .add(Book("book2"))
                .add(Book("book3"))
                .add(Book("book3"))

        assertEquals(loadedBasket.getPrice(), 29.6)
    }
}