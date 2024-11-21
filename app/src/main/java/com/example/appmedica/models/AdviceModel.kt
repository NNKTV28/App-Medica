data class Advice(
    val id: Int,
    val text: String
)

data class AdviceResponse(
    val advices: List<Advice>
)
