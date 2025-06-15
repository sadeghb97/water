package ir.sbpro.waterengineering.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.unit.DpOffset
import ir.sbpro.waterengineering.AppDataStore
import ir.sbpro.waterengineering.AppSingleton
import ir.sbpro.waterengineering.R
import ir.sbpro.waterengineering.formulas.DFormula
import ir.sbpro.waterengineering.formulas.HFormula
import ir.sbpro.waterengineering.formulas.ParametersState
import ir.sbpro.waterengineering.formulas.VFormula
import ir.sbpro.waterengineering.formulas.WaterEngFormula
import ir.sbpro.waterengineering.lang.AppLanguage
import ir.sbpro.waterengineering.ui.components.NumberPad
import ir.sbpro.waterengineering.ui.components.ParamField
import ir.sbpro.waterengineering.ui.components.ScreenWrapper
import ir.sbpro.waterengineering.utils.dxp
import ir.sbpro.waterengineering.utils.sxp
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    lang: AppLanguage
){
    val appSingleton = AppSingleton.getInstance()
    val appDataStore = appSingleton.appDataStore

    val bottomBoxHeight = 325.dxp

    val formulasList = listOf<WaterEngFormula>(
        HFormula(), VFormula(), DFormula()
    )

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val lang by appDataStore.languageFlow.collectAsState(appSingleton.startLanguage)
    var isLangMenuExpanded by remember { mutableStateOf(false) }
    val activeFormulaIndex = remember { mutableIntStateOf(0) }
    val activeParamIndex: MutableState<Int?> = remember { mutableStateOf(null) }

    val activeFormula = formulasList[activeFormulaIndex.intValue]
    val parametersState = remember { mutableStateOf(ParametersState.getParams(activeFormula.formulaKey, activeFormula.parameters.size)) }
    val rowCells = remember { mutableIntStateOf(
        if(activeFormula.parameters.size <= 3) activeFormula.parameters.size
        else if (activeFormula.parameters.size == 4) 2
        else 3
    ) }

    ScreenWrapper {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(bottom = bottomBoxHeight, top = 20.dxp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dxp, start = 12.dxp, end = 12.dxp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {

                }) {
                    Image(
                        painter = painterResource(id = R.drawable.info),
                        contentDescription = lang.menu(),
                        modifier = Modifier.size(24.dxp),
                    )
                }

                Text(
                    text = lang.appName(),
                    fontSize = 20.sxp,
                    color = Color.White
                )

                Box {
                    IconButton(onClick = { isLangMenuExpanded = true }) {
                        Image(
                            painter = painterResource(id = R.drawable.translate),
                            contentDescription = lang.menu(),
                            modifier = Modifier.size(24.dxp),
                        )
                    }

                    DropdownMenu(
                        expanded = isLangMenuExpanded,
                        onDismissRequest = { isLangMenuExpanded = false },
                        offset = DpOffset(x = 36.dxp, y = 0.dxp)
                    ) {
                        DropdownMenuItem(
                            text = { Text("فارسی") },
                            onClick = {
                                coroutineScope.launch {
                                    appDataStore.safeTransaction {
                                        it[AppDataStore.LANGUAGE_KEY] = "fa"
                                    }
                                    appSingleton.saveLanguage(context, "fa")
                                }
                                isLangMenuExpanded = false
                            },
                            leadingIcon = {
                                if (lang.getLangCode() == "fa")
                                    Icon(Icons.Default.Check, contentDescription = null)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("English") },
                            onClick = {
                                coroutineScope.launch {
                                    appDataStore.safeTransaction {
                                        it[AppDataStore.LANGUAGE_KEY] = "en"
                                    }
                                    appSingleton.saveLanguage(context, "en")
                                }
                                isLangMenuExpanded = false
                            },
                            leadingIcon = {
                                if (lang.getLangCode() == "en")
                                    Icon(Icons.Default.Check, contentDescription = null)
                            }
                        )
                    }
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(rowCells.intValue),
                modifier = Modifier
                    .padding(start = 20.dxp, end = 20.dxp, bottom = 12.dxp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dxp),
                horizontalArrangement = Arrangement.spacedBy(12.dxp),
                userScrollEnabled = false
            ) {
                parametersState.value.params.forEachIndexed { index, item ->
                    item {
                        ParamField(index.toString(), item, parametersState.value.focuses[index]){
                            activeParamIndex.value = index
                        }
                    }
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
                .height(bottomBoxHeight)
                .align(Alignment.BottomCenter)
        ) {
            NumberPad(
                activeParam = if(activeParamIndex.value != null) parametersState.value.params[activeParamIndex.value!!] else null,
                formulas = formulasList,
                onFormulaChange = {
                    activeFormulaIndex.intValue = it
                    val cf = formulasList[activeFormulaIndex.intValue]
                    rowCells.intValue =
                        if(cf.parameters.size <= 3) cf.parameters.size
                        else if (cf.parameters.size == 4) 2
                        else 3
                    parametersState.value = ParametersState.getParams(cf.formulaKey, cf.parameters.size)
                },
                onNextParameter = {
                    if(activeParamIndex.value == null) activeParamIndex.value = 0
                    else if(activeParamIndex.value!! < (parametersState.value.params.size - 1))
                        activeParamIndex.value = activeParamIndex.value!! + 1
                    else activeParamIndex.value = 0

                    val nextState = parametersState.value.params[activeParamIndex.value!!]
                    parametersState.value.focuses[activeParamIndex.value!!].requestFocus()
                    nextState.value = nextState.value.copy(
                        selection = TextRange(0, nextState.value.text.length)
                    )
                }
            ){

            }
        }
    }
}