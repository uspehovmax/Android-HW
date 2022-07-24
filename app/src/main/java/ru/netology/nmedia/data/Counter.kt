package ru.netology.nmedia.data

fun counter(numberLikes: Int): String {
    var number2digits: Any
    var text = ""
    if (numberLikes in 1000..9999) {    // знак после ,
        number2digits = (numberLikes / 100) / 10.0
        text = "K"
    } else if (numberLikes in 10_000..999_999) { // только целые числа >=10 до 999
        number2digits = (numberLikes / 1000)
        text = "K"
    } else if (numberLikes > 999_999) {
        number2digits = (numberLikes / 100_000) / 10.0
        text = "M"
    } else {
        number2digits = numberLikes
    }
    return number2digits.toString() + text
}
