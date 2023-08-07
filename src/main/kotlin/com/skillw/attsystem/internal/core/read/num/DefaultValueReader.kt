package com.skillw.attsystem.internal.core.read.num

object DefaultValueReader : NumberReader(
    "default", mapOf(
        "percentMax" to "plus",
        "percentMin" to "plus",
        "valueMin" to "plus",
        "valueMax" to "plus",
        "percent" to "plus",
        "value" to "plus",
        "scalar" to "scalar"
    ), listOf(
        "{name}(:|��)?\\s?<percentMin>-<percentMax>\\(%\\)",
        "{name}(:|��)?\\s?<valueMin>-<valueMax>",
        "{name}(:|��)?\\s?<percent>\\(%\\)",
        "{name}(:|��)?\\s?<value>",
        "{name}\\*<scalar>"
    ),
    mapOf(
        "total" to "( <value> + {random <valueMin> to <valueMax>})*(1+(<percent>/100 + {random <percentMin> to <percentMax>} /100)) * { if check <scalar> == 0 then 1 else <scalar> }",
        "scalar" to "<scalar>",
        "value" to "<value>",
        "percent" to "<percent>/100",
        "valueMin" to "<valueMin>",
        "valueMax" to "<valueMax>",
        "percentMin" to "<percentMin>/100",
        "percentMax" to "<percentMax>/100",
        "valueRandom" to "<value> + {random <valueMin> to <valueMax>}",
        "percentRandom" to "(<percent>/100 + {random <percentMin> to <percentMax>}/100)"
    )
) {
    
}