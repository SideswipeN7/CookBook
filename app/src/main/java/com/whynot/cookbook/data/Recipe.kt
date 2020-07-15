package com.whynot.cookbook.data

data class Recipe(
    val id: Int,
    val name: String,
    val image: ByteArray,
    val synopsis: String,
    val categoryId: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Recipe

        if (id != other.id) return false
        if (name != other.name) return false
        if (!image.contentEquals(other.image)) return false
        if (synopsis != other.synopsis) return false
        if (categoryId != other.categoryId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + image.contentHashCode()
        result = 31 * result + synopsis.hashCode()
        result = 31 * result + categoryId
        return result
    }
}