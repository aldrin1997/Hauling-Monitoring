package com.asdec.hauling.ui.screens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000B\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0002\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\"\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007\u001a\u001a\u0010\f\u001a\u00020\u00012\b\u0010\r\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0007\u001a\u00020\bH\u0007\u001a:\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00010\u0014H\u0007\u001a2\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u00a8\u0006\u001a"}, d2 = {"ActiveTruckCard", "", "truck", "Lcom/asdec/hauling/data/Truck;", "onExitClick", "Lkotlin/Function0;", "DetailColumn", "label", "", "value", "isPrimary", "", "PhotoPreview", "path", "TruckDetailsScreen", "batchId", "viewModel", "Lcom/asdec/hauling/ui/MainViewModel;", "onBack", "onNavigateToExit", "Lkotlin/Function1;", "", "TruckInfoCard", "photoPaths", "", "onDeleteClick", "app_debug"})
public final class TruckDetailsScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void TruckDetailsScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String batchId, @org.jetbrains.annotations.NotNull()
    com.asdec.hauling.ui.MainViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onNavigateToExit) {
    }
    
    /**
     * Special Card for trucks that have arrived but NOT yet exited.
     */
    @androidx.compose.runtime.Composable()
    public static final void ActiveTruckCard(@org.jetbrains.annotations.NotNull()
    com.asdec.hauling.data.Truck truck, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onExitClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void TruckInfoCard(@org.jetbrains.annotations.NotNull()
    com.asdec.hauling.data.Truck truck, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> photoPaths, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDeleteClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void DetailColumn(@org.jetbrains.annotations.NotNull()
    java.lang.String label, @org.jetbrains.annotations.NotNull()
    java.lang.String value, boolean isPrimary) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void PhotoPreview(@org.jetbrains.annotations.Nullable()
    java.lang.String path, @org.jetbrains.annotations.NotNull()
    java.lang.String label) {
    }
}