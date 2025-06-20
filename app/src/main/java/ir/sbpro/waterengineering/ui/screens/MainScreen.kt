package ir.sbpro.waterengineering.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.navigation.NavController
import ir.sbpro.waterengineering.AppDataStore
import ir.sbpro.waterengineering.AppSingleton
import ir.sbpro.waterengineering.R
import ir.sbpro.waterengineering.formulas.DFormula
import ir.sbpro.waterengineering.formulas.FFormula
import ir.sbpro.waterengineering.formulas.FormulaResult
import ir.sbpro.waterengineering.formulas.HFormula
import ir.sbpro.waterengineering.formulas.ParametersState
import ir.sbpro.waterengineering.formulas.VFormula
import ir.sbpro.waterengineering.formulas.WaterEngFormula
import ir.sbpro.waterengineering.models.AppSettings
import ir.sbpro.waterengineering.ui.components.NumberPad
import ir.sbpro.waterengineering.ui.components.ParamField
import ir.sbpro.waterengineering.ui.components.ResultDisplay
import ir.sbpro.waterengineering.ui.components.ScreenWrapper
import ir.sbpro.waterengineering.ui.dialogs.AboutUsDialog
import ir.sbpro.waterengineering.utils.dxp
import ir.sbpro.waterengineering.utils.sxp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(navController: NavController){
    val appSingleton = AppSingleton.getInstance()
    val appDataStore = appSingleton.appDataStore

    val bottomBoxHeight = 250.dxp

    val formulasList = listOf<WaterEngFormula>(
        FFormula(), HFormula(), VFormula(), DFormula()
    )

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val coroutineScope = rememberCoroutineScope()
    val lang by appDataStore.languageFlow.collectAsState(appSingleton.startLanguage)
    val appSettings by appDataStore.appSettingsFlow.collectAsState(AppSettings())
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var isLangMenuExpanded = remember { mutableStateOf(false) }
    val aboutShow = remember { mutableStateOf(false) }
    val aboutAnimVisible = remember { mutableStateOf(false) }
    val activeFormulaIndex = remember { mutableIntStateOf(0) }
    val activeParamIndex: MutableState<Int?> = remember { mutableStateOf(null) }

    val activeFormula = remember { mutableStateOf(formulasList[activeFormulaIndex.intValue]) }
    val parametersState = remember {
        mutableStateOf(ParametersState.getParams(activeFormula.value))
    }
    val formulaResults: MutableState<List<FormulaResult>> = remember {
        mutableStateOf(activeFormula.value.getResults(parametersState.value))
    }

    val rowCells = remember { mutableIntStateOf(
        if(activeFormula.value.parameters.size <= 1) 1
        else 2
    ) }

    val dialogShowing = aboutShow.value

    BackHandler(enabled = dialogShowing) {
        aboutShow.value = false
    }

    ScreenWrapper(
        appSettings = appSettings,
        lang = lang,
        navController = navController,
        drawerState = drawerState,
        onClick = {
            focusManager.clearFocus()
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(bottom = bottomBoxHeight, top = 35.dxp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dxp, start = 16.dxp, end = 16.dxp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }) {
                    Icon(
                        Icons.Default.Menu,
                        modifier = Modifier.size(30.dxp),
                        contentDescription = lang.menu(),
                        tint = appSettings.lightColor
                    )
                }

                Text(
                    text = lang.appName(),
                    fontSize = 22.sxp,
                    fontWeight = FontWeight.Bold,
                    color = appSettings.lightColor
                )

                Box {
                    IconButton(onClick = { isLangMenuExpanded.value = true }) {
                        Image(
                            painter = painterResource(id = R.drawable.translate),
                            colorFilter = ColorFilter.tint(appSettings.lightColor),
                            contentDescription = lang.menu(),
                            modifier = Modifier.size(30.dxp),
                        )
                    }

                    DropdownMenu(
                        expanded = isLangMenuExpanded.value,
                        onDismissRequest = { isLangMenuExpanded.value = false },
                        offset = DpOffset(x = (-5).dxp, y = 0.dxp)
                    ) {
                        DropdownMenuItem(
                            text = { Text("فارسی") },
                            onClick = {
                                coroutineScope.launch {
                                    appDataStore.safeTransaction {
                                        it[AppDataStore.LANGUAGE_KEY] = "fa"
                                    }
                                    appDataStore.saveSharedLanguage("fa")
                                }
                                isLangMenuExpanded.value = false
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
                                    appDataStore.saveSharedLanguage("en")
                                }
                                isLangMenuExpanded.value = false
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
                    .padding(start = 20.dxp, end = 20.dxp, bottom = 16.dxp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dxp),
                horizontalArrangement = Arrangement.spacedBy(12.dxp),
                userScrollEnabled = false
            ) {
                parametersState.value.params.forEachIndexed { index, item ->
                    item {
                        ParamField(
                            appSettings = appSettings,
                            label = lang.getParameterTitle(activeFormula.value.parameters[index]),
                            textState = item,
                            focusRequester = parametersState.value.focuses[index]
                        ){
                            if(activeParamIndex.value != index) {
                                activeParamIndex.value = index
                                coroutineScope.launch {
                                    delay(240)
                                    val ps = parametersState.value.params[index]
                                    ps.value = ps.value.copy(
                                        selection = TextRange(0, ps.value.text.length)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.padding(horizontal = 14.dxp)
            ) {
                formulaResults.value.forEachIndexed { rIndex, result ->
                    ResultDisplay(
                        appSettings = appSettings,
                        lang = lang,
                        label = lang.getParameterTitle(result.key),
                        value = result.value?.toString(),
                        modifier = Modifier.weight(1f),
                        minimal = formulaResults.value.size > 1
                    )
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
                appSettings = appSettings,
                lang = lang,
                focusManager = focusManager,
                activeParam = if (activeParamIndex.value != null) parametersState.value.params[activeParamIndex.value!!] else null,
                formulas = formulasList,
                onFormulaChange = {
                    focusManager.clearFocus()
                    activeParamIndex.value = null
                    activeFormulaIndex.intValue = it
                    activeFormula.value = formulasList[activeFormulaIndex.intValue]
                    rowCells.intValue =
                        if(activeFormula.value.parameters.size <= 1) 1
                        else 2
                    parametersState.value =
                        ParametersState.getParams(activeFormula.value)
                    formulaResults.value = activeFormula.value.getResults(parametersState.value)
                },
                onNextParameter = {
                    if (activeParamIndex.value == null) activeParamIndex.value = 0
                    else if (activeParamIndex.value!! < (parametersState.value.params.size - 1))
                        activeParamIndex.value = activeParamIndex.value!! + 1
                    else activeParamIndex.value = 0

                    val nextState = parametersState.value.params[activeParamIndex.value!!]
                    parametersState.value.focuses[activeParamIndex.value!!].requestFocus()
                    nextState.value = nextState.value.copy(
                        selection = TextRange(0, nextState.value.text.length)
                    )
                }
            ) {
                formulaResults.value = activeFormula.value.getResults(parametersState.value)
                appDataStore.saveFormulaResult(activeFormula.value, parametersState.value, formulaResults.value)
            }
        }
    }

    AboutUsDialog(appSettings = appSettings, lang = lang, active = aboutShow.value, animationVisible = aboutAnimVisible.value) {
        coroutineScope.launch {
            aboutAnimVisible.value = false
            aboutShow.value = false
        }
    }
}