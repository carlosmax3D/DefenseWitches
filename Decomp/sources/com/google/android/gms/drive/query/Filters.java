package com.google.android.gms.drive.query;

import com.google.android.gms.drive.metadata.CollectionMetadataField;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.OrderedMetadataField;
import com.google.android.gms.drive.query.internal.ComparisonFilter;
import com.google.android.gms.drive.query.internal.FieldOnlyFilter;
import com.google.android.gms.drive.query.internal.InFilter;
import com.google.android.gms.drive.query.internal.LogicalFilter;
import com.google.android.gms.drive.query.internal.NotFilter;
import com.google.android.gms.drive.query.internal.Operator;
import java.util.List;

public class Filters {
    public static Filter and(Filter filter, Filter... filterArr) {
        return new LogicalFilter(Operator.si, filter, filterArr);
    }

    public static Filter and(List<Filter> list) {
        return new LogicalFilter(Operator.si, list);
    }

    public static Filter contains(MetadataField<String> metadataField, String str) {
        return new ComparisonFilter(Operator.sl, metadataField, str);
    }

    public static <T> Filter eq(MetadataField<T> metadataField, T t) {
        return new ComparisonFilter(Operator.sd, metadataField, t);
    }

    public static <T extends Comparable<T>> Filter greaterThan(OrderedMetadataField<T> orderedMetadataField, T t) {
        return new ComparisonFilter(Operator.sg, orderedMetadataField, t);
    }

    public static <T extends Comparable<T>> Filter greaterThanEquals(OrderedMetadataField<T> orderedMetadataField, T t) {
        return new ComparisonFilter(Operator.sh, orderedMetadataField, t);
    }

    public static <T> Filter in(CollectionMetadataField<T> collectionMetadataField, T t) {
        return new InFilter(collectionMetadataField, t);
    }

    public static <T extends Comparable<T>> Filter lessThan(OrderedMetadataField<T> orderedMetadataField, T t) {
        return new ComparisonFilter(Operator.se, orderedMetadataField, t);
    }

    public static <T extends Comparable<T>> Filter lessThanEquals(OrderedMetadataField<T> orderedMetadataField, T t) {
        return new ComparisonFilter(Operator.sf, orderedMetadataField, t);
    }

    public static Filter not(Filter filter) {
        return new NotFilter(filter);
    }

    public static Filter or(Filter filter, Filter... filterArr) {
        return new LogicalFilter(Operator.sj, filter, filterArr);
    }

    public static Filter or(List<Filter> list) {
        return new LogicalFilter(Operator.sj, list);
    }

    public static Filter sharedWithMe() {
        return new FieldOnlyFilter(SearchableField.rM);
    }
}
