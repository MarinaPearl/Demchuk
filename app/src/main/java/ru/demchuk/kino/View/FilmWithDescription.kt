package ru.demchuk.kino.View

data class FilmWithDescription(
    var posterUrlPreview : String?,
    var description : String?,
    var genres : ArrayList<String?>,
    var country : ArrayList<String?>
)
