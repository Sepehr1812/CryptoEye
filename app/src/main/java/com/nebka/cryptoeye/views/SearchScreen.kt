package com.nebka.cryptoeye.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nebka.cryptoeye.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen() {

    var query by rememberSaveable { mutableStateOf("") }
    var isActive by rememberSaveable { mutableStateOf(false) }
    val searchHistory = remember { mutableStateListOf("") }

    Column(Modifier.fillMaxSize()) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            query = query,
            onQueryChange = { query = it },
            onSearch = {
                isActive = false
                searchHistory.add(it)
            },
            active = isActive,
            onActiveChange = { isActive = it },
            placeholder = { Text(stringResource(R.string.search_tags)) },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_icon_desc)
                )
            },
            trailingIcon = {
                if (isActive) {
                    Icon(
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                if (query.isNotEmpty()) query = ""
                                else isActive = false
                            },
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.close_icon_desc)
                    )
                }
            },
        ) {
            if (isActive) {
                searchHistory.forEach {
                    if (it.isNotEmpty()) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 16.dp)
                            .clickable {
                                query = it
                                isActive = false
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = stringResource(R.string.history_icon_desc)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = it)
                        }
                    }
                }
            }
        }


    }
}