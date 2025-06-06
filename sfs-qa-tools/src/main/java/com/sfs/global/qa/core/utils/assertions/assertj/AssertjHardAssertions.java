package com.sfs.global.qa.core.utils.assertions.assertj;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractBigDecimalAssert;
import org.assertj.core.api.AbstractBigIntegerAssert;
import org.assertj.core.api.AbstractBooleanArrayAssert;
import org.assertj.core.api.AbstractBooleanAssert;
import org.assertj.core.api.AbstractByteArrayAssert;
import org.assertj.core.api.AbstractByteAssert;
import org.assertj.core.api.AbstractCharArrayAssert;
import org.assertj.core.api.AbstractCharSequenceAssert;
import org.assertj.core.api.AbstractCharacterAssert;
import org.assertj.core.api.AbstractCollectionAssert;
import org.assertj.core.api.AbstractComparableAssert;
import org.assertj.core.api.AbstractDateAssert;
import org.assertj.core.api.AbstractDoubleArrayAssert;
import org.assertj.core.api.AbstractDoubleAssert;
import org.assertj.core.api.AbstractDurationAssert;
import org.assertj.core.api.AbstractFileAssert;
import org.assertj.core.api.AbstractFloatArrayAssert;
import org.assertj.core.api.AbstractFloatAssert;
import org.assertj.core.api.AbstractInputStreamAssert;
import org.assertj.core.api.AbstractInstantAssert;
import org.assertj.core.api.AbstractIntArrayAssert;
import org.assertj.core.api.AbstractIntegerAssert;
import org.assertj.core.api.AbstractLocalDateAssert;
import org.assertj.core.api.AbstractLocalDateTimeAssert;
import org.assertj.core.api.AbstractLocalTimeAssert;
import org.assertj.core.api.AbstractLongArrayAssert;
import org.assertj.core.api.AbstractLongAssert;
import org.assertj.core.api.AbstractOffsetDateTimeAssert;
import org.assertj.core.api.AbstractOffsetTimeAssert;
import org.assertj.core.api.AbstractPathAssert;
import org.assertj.core.api.AbstractPeriodAssert;
import org.assertj.core.api.AbstractShortArrayAssert;
import org.assertj.core.api.AbstractShortAssert;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.AbstractUniversalComparableAssert;
import org.assertj.core.api.AbstractUriAssert;
import org.assertj.core.api.AbstractUrlAssert;
import org.assertj.core.api.AbstractZonedDateTimeAssert;
import org.assertj.core.api.AssertDelegateTarget;
import org.assertj.core.api.AssertFactory;
import org.assertj.core.api.AssertProvider;
import org.assertj.core.api.AssertionsForClassTypes;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.assertj.core.api.AtomicBooleanAssert;
import org.assertj.core.api.AtomicIntegerArrayAssert;
import org.assertj.core.api.AtomicIntegerAssert;
import org.assertj.core.api.AtomicIntegerFieldUpdaterAssert;
import org.assertj.core.api.AtomicLongArrayAssert;
import org.assertj.core.api.AtomicLongAssert;
import org.assertj.core.api.AtomicLongFieldUpdaterAssert;
import org.assertj.core.api.AtomicMarkableReferenceAssert;
import org.assertj.core.api.AtomicReferenceArrayAssert;
import org.assertj.core.api.AtomicReferenceAssert;
import org.assertj.core.api.AtomicReferenceFieldUpdaterAssert;
import org.assertj.core.api.AtomicStampedReferenceAssert;
import org.assertj.core.api.BigIntegerAssert;
import org.assertj.core.api.Boolean2DArrayAssert;
import org.assertj.core.api.Byte2DArrayAssert;
import org.assertj.core.api.Char2DArrayAssert;
import org.assertj.core.api.ClassAssert;
import org.assertj.core.api.ClassBasedNavigableIterableAssert;
import org.assertj.core.api.ClassBasedNavigableListAssert;
import org.assertj.core.api.CompletableFutureAssert;
import org.assertj.core.api.Condition;
import org.assertj.core.api.Double2DArrayAssert;
import org.assertj.core.api.DoublePredicateAssert;
import org.assertj.core.api.FactoryBasedNavigableIterableAssert;
import org.assertj.core.api.FactoryBasedNavigableListAssert;
import org.assertj.core.api.Fail;
import org.assertj.core.api.Float2DArrayAssert;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.api.InstanceOfAssertFactory;
import org.assertj.core.api.Int2DArrayAssert;
import org.assertj.core.api.IntPredicateAssert;
import org.assertj.core.api.IterableAssert;
import org.assertj.core.api.IteratorAssert;
import org.assertj.core.api.ListAssert;
import org.assertj.core.api.Long2DArrayAssert;
import org.assertj.core.api.LongAdderAssert;
import org.assertj.core.api.LongPredicateAssert;
import org.assertj.core.api.MapAssert;
import org.assertj.core.api.MatcherAssert;
import org.assertj.core.api.NotThrownAssert;
import org.assertj.core.api.Object2DArrayAssert;
import org.assertj.core.api.ObjectArrayAssert;
import org.assertj.core.api.ObjectAssert;
import org.assertj.core.api.OptionalAssert;
import org.assertj.core.api.OptionalDoubleAssert;
import org.assertj.core.api.OptionalIntAssert;
import org.assertj.core.api.OptionalLongAssert;
import org.assertj.core.api.PredicateAssert;
import org.assertj.core.api.Short2DArrayAssert;
import org.assertj.core.api.SpliteratorAssert;
import org.assertj.core.api.ThrowableAssert;
import org.assertj.core.api.ThrowableTypeAssert;
import org.assertj.core.api.filter.Filters;
import org.assertj.core.api.filter.InFilter;
import org.assertj.core.api.filter.NotFilter;
import org.assertj.core.api.filter.NotInFilter;
import org.assertj.core.condition.AllOf;
import org.assertj.core.condition.AnyOf;
import org.assertj.core.condition.DoesNotHave;
import org.assertj.core.condition.Not;
import org.assertj.core.configuration.ConfigurationProvider;
import org.assertj.core.data.Index;
import org.assertj.core.data.MapEntry;
import org.assertj.core.data.Offset;
import org.assertj.core.data.Percentage;
import org.assertj.core.data.TemporalUnitLessThanOffset;
import org.assertj.core.data.TemporalUnitOffset;
import org.assertj.core.data.TemporalUnitWithinOffset;
import org.assertj.core.description.Description;
import org.assertj.core.groups.Properties;
import org.assertj.core.groups.Tuple;
import org.assertj.core.presentation.Representation;
import org.assertj.core.presentation.StandardRepresentation;
import org.assertj.core.util.CanIgnoreReturnValue;
import org.assertj.core.util.Files;
import org.assertj.core.util.Paths;
import org.assertj.core.util.URLs;
import org.assertj.core.util.introspection.FieldSupport;
import org.assertj.core.util.introspection.Introspection;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.text.DateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalUnit;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Spliterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.DoublePredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class AssertjHardAssertions implements InstanceOfAssertFactories {

    protected AssertjHardAssertions() {
    }

    public <T> PredicateAssert<T> assertThat(Predicate<T> actual) {
        return AssertionsForInterfaceTypes.assertThat(actual);
    }

    public <T> PredicateAssert<T> assertThatPredicate(Predicate<T> actual) {
        return assertThat(actual);
    }

    public IntPredicateAssert assertThat(IntPredicate actual) {
        return AssertionsForInterfaceTypes.assertThat(actual);
    }

    public LongPredicateAssert assertThat(LongPredicate actual) {
        return AssertionsForInterfaceTypes.assertThat(actual);
    }

    public DoublePredicateAssert assertThat(DoublePredicate actual) {
        return AssertionsForInterfaceTypes.assertThat(actual);
    }

    public <RESULT> CompletableFutureAssert<RESULT> assertThat(CompletableFuture<RESULT> actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public <RESULT> CompletableFutureAssert<RESULT> assertThat(CompletionStage<RESULT> actual) {
        return AssertionsForInterfaceTypes.assertThat(actual);
    }

    public <VALUE> OptionalAssert<VALUE> assertThat(Optional<VALUE> actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public OptionalDoubleAssert assertThat(OptionalDouble actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public OptionalIntAssert assertThat(OptionalInt actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public OptionalLongAssert assertThat(OptionalLong actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public MatcherAssert assertThat(Matcher actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractBigDecimalAssert<?> assertThat(BigDecimal actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractBigIntegerAssert<?> assertThat(BigInteger actual) {
        return new BigIntegerAssert(actual);
    }

    public AbstractUriAssert<?> assertThat(URI actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractUrlAssert<?> assertThat(URL actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractBooleanAssert<?> assertThat(boolean actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractBooleanAssert<?> assertThat(Boolean actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractBooleanArrayAssert<?> assertThat(boolean[] actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public Boolean2DArrayAssert assertThat(boolean[][] actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractByteAssert<?> assertThat(byte actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractByteAssert<?> assertThat(Byte actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractByteArrayAssert<?> assertThat(byte[] actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public Byte2DArrayAssert assertThat(byte[][] actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractCharacterAssert<?> assertThat(char actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractCharArrayAssert<?> assertThat(char[] actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public Char2DArrayAssert assertThat(char[][] actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractCharacterAssert<?> assertThat(Character actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public ClassAssert assertThat(Class<?> actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractDoubleAssert<?> assertThat(double actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractDoubleAssert<?> assertThat(Double actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractDoubleArrayAssert<?> assertThat(double[] actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public Double2DArrayAssert assertThat(double[][] actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractFileAssert<?> assertThat(File actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    /*public <RESULT> FutureAssert<RESULT> assertThat(Future<RESULT> actual) {
        return new FutureAssert(actual);
    }*/

    public AbstractInputStreamAssert<?, ? extends InputStream> assertThat(InputStream actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractFloatAssert<?> assertThat(float actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractFloatAssert<?> assertThat(Float actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractFloatArrayAssert<?> assertThat(float[] actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractIntegerAssert<?> assertThat(int actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractIntArrayAssert<?> assertThat(int[] actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public Int2DArrayAssert assertThat(int[][] actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public Float2DArrayAssert assertThat(float[][] actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractIntegerAssert<?> assertThat(Integer actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public <ACTUAL extends Iterable<? extends ELEMENT>, ELEMENT, ELEMENT_ASSERT extends AbstractAssert<ELEMENT_ASSERT, ELEMENT>> FactoryBasedNavigableIterableAssert<?, ACTUAL, ELEMENT, ELEMENT_ASSERT> assertThat(Iterable<? extends ELEMENT> actual, AssertFactory<ELEMENT, ELEMENT_ASSERT> assertFactory) {
        return AssertionsForInterfaceTypes.assertThat(actual, assertFactory);
    }

    public <ACTUAL extends Iterable<? extends ELEMENT>, ELEMENT, ELEMENT_ASSERT extends AbstractAssert<ELEMENT_ASSERT, ELEMENT>> ClassBasedNavigableIterableAssert<?, ACTUAL, ELEMENT, ELEMENT_ASSERT> assertThat(ACTUAL actual, Class<ELEMENT_ASSERT> assertClass) {
        return AssertionsForInterfaceTypes.assertThat(actual, assertClass);
    }

    public <ACTUAL extends List<? extends ELEMENT>, ELEMENT, ELEMENT_ASSERT extends AbstractAssert<ELEMENT_ASSERT, ELEMENT>> FactoryBasedNavigableListAssert<?, ACTUAL, ELEMENT, ELEMENT_ASSERT> assertThat(List<? extends ELEMENT> actual, AssertFactory<ELEMENT, ELEMENT_ASSERT> assertFactory) {
        return AssertionsForInterfaceTypes.assertThat(actual, assertFactory);
    }

    public <ELEMENT, ACTUAL extends List<? extends ELEMENT>, ELEMENT_ASSERT extends AbstractAssert<ELEMENT_ASSERT, ELEMENT>> ClassBasedNavigableListAssert<?, ACTUAL, ELEMENT, ELEMENT_ASSERT> assertThat(List<? extends ELEMENT> actual, Class<ELEMENT_ASSERT> assertClass) {
        return AssertionsForInterfaceTypes.assertThat(actual, assertClass);
    }

    public AbstractLongAssert<?> assertThat(long actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractLongAssert<?> assertThat(Long actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractLongArrayAssert<?> assertThat(long[] actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public Long2DArrayAssert assertThat(long[][] actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public <T> ObjectAssert<T> assertThat(T actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public <T> ObjectArrayAssert<T> assertThat(T[] actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public <T> Object2DArrayAssert<T> assertThat(T[][] actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractShortAssert<?> assertThat(short actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractShortAssert<?> assertThat(Short actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractShortArrayAssert<?> assertThat(short[] actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public Short2DArrayAssert assertThat(short[][] actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractDateAssert<?> assertThat(Date actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractZonedDateTimeAssert<?> assertThat(ZonedDateTime actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractLocalDateTimeAssert<?> assertThat(LocalDateTime actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractOffsetDateTimeAssert<?> assertThat(OffsetDateTime actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractOffsetTimeAssert<?> assertThat(OffsetTime actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractLocalTimeAssert<?> assertThat(LocalTime actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractLocalDateAssert<?> assertThat(LocalDate actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractInstantAssert<?> assertThat(Instant actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractDurationAssert<?> assertThat(Duration actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractPeriodAssert<?> assertThat(Period actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AtomicBooleanAssert assertThat(AtomicBoolean actual) {
        return new AtomicBooleanAssert(actual);
    }

    public AtomicIntegerAssert assertThat(AtomicInteger actual) {
        return new AtomicIntegerAssert(actual);
    }

    public AtomicIntegerArrayAssert assertThat(AtomicIntegerArray actual) {
        return new AtomicIntegerArrayAssert(actual);
    }

    public <OBJECT> AtomicIntegerFieldUpdaterAssert<OBJECT> assertThat(AtomicIntegerFieldUpdater<OBJECT> actual) {
        return new AtomicIntegerFieldUpdaterAssert(actual);
    }

    public LongAdderAssert assertThat(LongAdder actual) {
        return new LongAdderAssert(actual);
    }

    public AtomicLongAssert assertThat(AtomicLong actual) {
        return new AtomicLongAssert(actual);
    }

    public AtomicLongArrayAssert assertThat(AtomicLongArray actual) {
        return new AtomicLongArrayAssert(actual);
    }

    public <OBJECT> AtomicLongFieldUpdaterAssert<OBJECT> assertThat(AtomicLongFieldUpdater<OBJECT> actual) {
        return new AtomicLongFieldUpdaterAssert(actual);
    }

    public <VALUE> AtomicReferenceAssert<VALUE> assertThat(AtomicReference<VALUE> actual) {
        return new AtomicReferenceAssert(actual);
    }

    public <ELEMENT> AtomicReferenceArrayAssert<ELEMENT> assertThat(AtomicReferenceArray<ELEMENT> actual) {
        return new AtomicReferenceArrayAssert(actual);
    }

    public <FIELD, OBJECT> AtomicReferenceFieldUpdaterAssert<FIELD, OBJECT> assertThat(AtomicReferenceFieldUpdater<OBJECT, FIELD> actual) {
        return new AtomicReferenceFieldUpdaterAssert(actual);
    }

    public <VALUE> AtomicMarkableReferenceAssert<VALUE> assertThat(AtomicMarkableReference<VALUE> actual) {
        return new AtomicMarkableReferenceAssert(actual);
    }

    public <VALUE> AtomicStampedReferenceAssert<VALUE> assertThat(AtomicStampedReference<VALUE> actual) {
        return new AtomicStampedReferenceAssert(actual);
    }

    public <T extends Throwable> AbstractThrowableAssert<?, T> assertThat(T actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    /*@CanIgnoreReturnValue
    public AbstractThrowableAssert<?, ? extends Throwable> assertThatThrownBy(ThrowableAssert.ThrowingCallable shouldRaiseThrowable) {
        return assertThat(catchThrowable(shouldRaiseThrowable)).hasBeenThrown();
    }

    @CanIgnoreReturnValue
    public AbstractThrowableAssert<?, ? extends Throwable> assertThatThrownBy(ThrowableAssert.ThrowingCallable shouldRaiseThrowable, String description, Object... args) {
        return ((AbstractThrowableAssert) assertThat(catchThrowable(shouldRaiseThrowable)).as(description, args)).hasBeenThrown();
    }*/

    public AbstractThrowableAssert<?, ? extends Throwable> assertThatCode(ThrowableAssert.ThrowingCallable shouldRaiseOrNotThrowable) {
        return AssertionsForClassTypes.assertThatCode(shouldRaiseOrNotThrowable);
    }

    public <T> ObjectAssert<T> assertThatObject(T actual) {
        return assertThat(actual);
    }

    @SafeVarargs
    @CanIgnoreReturnValue
    public final <T> ObjectAssert<T> assertWith(T actual, Consumer<T>... requirements) {
        return (ObjectAssert) assertThat(actual).satisfies(requirements);
    }

    public Throwable catchThrowable(ThrowableAssert.ThrowingCallable shouldRaiseThrowable) {
        return AssertionsForClassTypes.catchThrowable(shouldRaiseThrowable);
    }

    public <THROWABLE extends Throwable> THROWABLE catchThrowableOfType(ThrowableAssert.ThrowingCallable shouldRaiseThrowable, Class<THROWABLE> type) {
        return AssertionsForClassTypes.catchThrowableOfType(shouldRaiseThrowable, type);
    }

    public Exception catchException(ThrowableAssert.ThrowingCallable shouldRaiseException) {
        return AssertionsForClassTypes.catchThrowableOfType(shouldRaiseException, Exception.class);
    }

    public RuntimeException catchRuntimeException(ThrowableAssert.ThrowingCallable shouldRaiseRuntimeException) {
        return AssertionsForClassTypes.catchThrowableOfType(shouldRaiseRuntimeException, RuntimeException.class);
    }

    public NullPointerException catchNullPointerException(ThrowableAssert.ThrowingCallable shouldRaiseNullPointerException) {
        return AssertionsForClassTypes.catchThrowableOfType(shouldRaiseNullPointerException, NullPointerException.class);
    }

    public IllegalArgumentException catchIllegalArgumentException(ThrowableAssert.ThrowingCallable shouldRaiseIllegalArgumentException) {
        return AssertionsForClassTypes.catchThrowableOfType(shouldRaiseIllegalArgumentException, IllegalArgumentException.class);
    }

    public IOException catchIOException(ThrowableAssert.ThrowingCallable shouldRaiseIOException) {
        return AssertionsForClassTypes.catchThrowableOfType(shouldRaiseIOException, IOException.class);
    }

    public ReflectiveOperationException catchReflectiveOperationException(ThrowableAssert.ThrowingCallable shouldRaiseReflectiveOperationException) {
        return AssertionsForClassTypes.catchThrowableOfType(shouldRaiseReflectiveOperationException, ReflectiveOperationException.class);
    }

    public IllegalStateException catchIllegalStateException(ThrowableAssert.ThrowingCallable shouldRaiseIllegalStateException) {
        return AssertionsForClassTypes.catchThrowableOfType(shouldRaiseIllegalStateException, IllegalStateException.class);
    }

    public IndexOutOfBoundsException catchIndexOutOfBoundsException(ThrowableAssert.ThrowingCallable shouldRaiseIndexOutOfBoundException) {
        return AssertionsForClassTypes.catchThrowableOfType(shouldRaiseIndexOutOfBoundException, IndexOutOfBoundsException.class);
    }

    public <T extends Throwable> ThrowableTypeAssert<T> assertThatExceptionOfType(final Class<? extends T> exceptionType) {
        return AssertionsForClassTypes.assertThatExceptionOfType(exceptionType);
    }

    public NotThrownAssert assertThatNoException() {
        return AssertionsForClassTypes.assertThatNoException();
    }

    public ThrowableTypeAssert<NullPointerException> assertThatNullPointerException() {
        return assertThatExceptionOfType(NullPointerException.class);
    }

    public ThrowableTypeAssert<IllegalArgumentException> assertThatIllegalArgumentException() {
        return assertThatExceptionOfType(IllegalArgumentException.class);
    }

    public ThrowableTypeAssert<IOException> assertThatIOException() {
        return assertThatExceptionOfType(IOException.class);
    }

    public ThrowableTypeAssert<IllegalStateException> assertThatIllegalStateException() {
        return assertThatExceptionOfType(IllegalStateException.class);
    }

    public ThrowableTypeAssert<Exception> assertThatException() {
        return assertThatExceptionOfType(Exception.class);
    }

    public ThrowableTypeAssert<RuntimeException> assertThatRuntimeException() {
        return assertThatExceptionOfType(RuntimeException.class);
    }

    public ThrowableTypeAssert<ReflectiveOperationException> assertThatReflectiveOperationException() {
        return assertThatExceptionOfType(ReflectiveOperationException.class);
    }

    public ThrowableTypeAssert<IndexOutOfBoundsException> assertThatIndexOutOfBoundsException() {
        return assertThatExceptionOfType(IndexOutOfBoundsException.class);
    }

    public void setRemoveAssertJRelatedElementsFromStackTrace(boolean removeAssertJRelatedElementsFromStackTrace) {
        Fail.setRemoveAssertJRelatedElementsFromStackTrace(removeAssertJRelatedElementsFromStackTrace);
    }

    @CanIgnoreReturnValue
    public <T> T fail(String failureMessage) {
        return Fail.fail(failureMessage);
    }

    @CanIgnoreReturnValue
    public <T> T fail(String failureMessage, Object... args) {
        return Fail.fail(failureMessage, args);
    }

    @CanIgnoreReturnValue
    public <T> T fail(String failureMessage, Throwable realCause) {
        return Fail.fail(failureMessage, realCause);
    }

    @CanIgnoreReturnValue
    public <T> T failBecauseExceptionWasNotThrown(Class<? extends Throwable> throwableClass) {
        return Fail.shouldHaveThrown(throwableClass);
    }

    @CanIgnoreReturnValue
    public <T> T shouldHaveThrown(Class<? extends Throwable> throwableClass) {
        return Fail.shouldHaveThrown(throwableClass);
    }

    public void setMaxLengthForSingleLineDescription(int maxLengthForSingleLineDescription) {
        StandardRepresentation.setMaxLengthForSingleLineDescription(maxLengthForSingleLineDescription);
    }

    public void setMaxElementsForPrinting(int maxElementsForPrinting) {
        StandardRepresentation.setMaxElementsForPrinting(maxElementsForPrinting);
    }

    public void setPrintAssertionsDescription(boolean printAssertionsDescription) {
        AbstractAssert.setPrintAssertionsDescription(printAssertionsDescription);
    }

    public void setDescriptionConsumer(Consumer<Description> descriptionConsumer) {
        AbstractAssert.setDescriptionConsumer(descriptionConsumer);
    }

    public void setMaxStackTraceElementsDisplayed(int maxStackTraceElementsDisplayed) {
        StandardRepresentation.setMaxStackTraceElementsDisplayed(maxStackTraceElementsDisplayed);
    }

    public <T> Properties<T> extractProperty(String propertyName, Class<T> propertyType) {
        return Properties.extractProperty(propertyName, propertyType);
    }

    public Properties<Object> extractProperty(String propertyName) {
        return Properties.extractProperty(propertyName);
    }

    public Tuple tuple(Object... values) {
        return Tuple.tuple(values);
    }

    public void setAllowExtractingPrivateFields(boolean allowExtractingPrivateFields) {
        FieldSupport.extraction().setAllowUsingPrivateFields(allowExtractingPrivateFields);
    }

    public void setAllowComparingPrivateFields(boolean allowComparingPrivateFields) {
        FieldSupport.comparison().setAllowUsingPrivateFields(allowComparingPrivateFields);
    }

    public void setExtractBareNamePropertyMethods(boolean barenamePropertyMethods) {
        Introspection.setExtractBareNamePropertyMethods(barenamePropertyMethods);
    }

    public <K, V> MapEntry<K, V> entry(K key, V value) {
        return MapEntry.entry(key, value);
    }

    public Index atIndex(int index) {
        return Index.atIndex(index);
    }

    public Offset<Double> offset(Double value) {
        return Offset.offset(value);
    }

    public Offset<Float> offset(Float value) {
        return Offset.offset(value);
    }

    public Offset<Double> within(Double value) {
        return Offset.offset(value);
    }

    public Offset<Double> withPrecision(Double value) {
        return Offset.offset(value);
    }

    public Offset<Float> within(Float value) {
        return Offset.offset(value);
    }

    public Offset<Float> withPrecision(Float value) {
        return Offset.offset(value);
    }

    public Offset<BigDecimal> within(BigDecimal value) {
        return Offset.offset(value);
    }

    public Offset<BigInteger> within(BigInteger value) {
        return Offset.offset(value);
    }

    public Offset<Byte> within(Byte value) {
        return Offset.offset(value);
    }

    public Offset<Integer> within(Integer value) {
        return Offset.offset(value);
    }

    public Offset<Short> within(Short value) {
        return Offset.offset(value);
    }

    public Offset<Long> within(Long value) {
        return Offset.offset(value);
    }

    public TemporalUnitOffset within(long value, TemporalUnit unit) {
        return new TemporalUnitWithinOffset(value, unit);
    }

    public Duration withMarginOf(Duration allowedDifference) {
        return allowedDifference;
    }

    public Percentage withinPercentage(Double value) {
        return Percentage.withPercentage(value);
    }

    public Percentage withinPercentage(Integer value) {
        return Percentage.withPercentage((double) value);
    }

    public Percentage withinPercentage(Long value) {
        return Percentage.withPercentage((double) value);
    }

    public Offset<Double> byLessThan(Double value) {
        return Offset.strictOffset(value);
    }

    public Offset<Float> byLessThan(Float value) {
        return Offset.strictOffset(value);
    }

    public Offset<BigDecimal> byLessThan(BigDecimal value) {
        return Offset.strictOffset(value);
    }

    public Offset<BigInteger> byLessThan(BigInteger value) {
        return Offset.strictOffset(value);
    }

    public Offset<Byte> byLessThan(Byte value) {
        return Offset.strictOffset(value);
    }

    public Offset<Integer> byLessThan(Integer value) {
        return Offset.strictOffset(value);
    }

    public Offset<Short> byLessThan(Short value) {
        return Offset.strictOffset(value);
    }

    public Offset<Long> byLessThan(Long value) {
        return Offset.strictOffset(value);
    }

    public TemporalUnitOffset byLessThan(long value, TemporalUnit unit) {
        return new TemporalUnitLessThanOffset(value, unit);
    }

    public <F, T> Function<F, T> from(Function<F, T> extractor) {
        return extractor;
    }

    public <T, ASSERT extends AbstractAssert<?, ?>> InstanceOfAssertFactory<T, ASSERT> as(InstanceOfAssertFactory<T, ASSERT> assertFactory) {
        return assertFactory;
    }

    @SafeVarargs
    public final <T> Condition<T> allOf(Condition<? super T>... conditions) {
        return AllOf.allOf(conditions);
    }

    public <T> Condition<T> allOf(Iterable<? extends Condition<? super T>> conditions) {
        return AllOf.allOf(conditions);
    }

    @SafeVarargs
    public final <T> Condition<T> anyOf(Condition<? super T>... conditions) {
        return AnyOf.anyOf(conditions);
    }

    public <T> Condition<T> anyOf(Iterable<? extends Condition<? super T>> conditions) {
        return AnyOf.anyOf(conditions);
    }

    public <T> DoesNotHave<T> doesNotHave(Condition<? super T> condition) {
        return DoesNotHave.doesNotHave(condition);
    }

    public <T> Not<T> not(Condition<? super T> condition) {
        return Not.not(condition);
    }

    public <E> Filters<E> filter(E[] array) {
        return Filters.filter(array);
    }

    public <E> Filters<E> filter(Iterable<E> iterableToFilter) {
        return Filters.filter(iterableToFilter);
    }

    public InFilter in(Object... values) {
        return InFilter.in(values);
    }

    public NotInFilter notIn(Object... valuesNotToMatch) {
        return NotInFilter.notIn(valuesNotToMatch);
    }

    public NotFilter not(Object valueNotToMatch) {
        return NotFilter.not(valueNotToMatch);
    }

    public String contentOf(File file, Charset charset) {
        return Files.contentOf(file, charset);
    }

    public String contentOf(File file, String charsetName) {
        return Files.contentOf(file, charsetName);
    }

    public String contentOf(File file) {
        return Files.contentOf(file, Charset.defaultCharset());
    }

    public List<String> linesOf(File file) {
        return Files.linesOf(file, Charset.defaultCharset());
    }

    public List<String> linesOf(File file, Charset charset) {
        return Files.linesOf(file, charset);
    }

    public List<String> linesOf(File file, String charsetName) {
        return Files.linesOf(file, charsetName);
    }

    public List<String> linesOf(Path path) {
        return Paths.linesOf(path, Charset.defaultCharset());
    }

    public List<String> linesOf(Path path, Charset charset) {
        return Paths.linesOf(path, charset);
    }

    public List<String> linesOf(Path path, String charsetName) {
        return Paths.linesOf(path, charsetName);
    }

    public String contentOf(URL url, Charset charset) {
        return URLs.contentOf(url, charset);
    }

    public String contentOf(URL url, String charsetName) {
        return URLs.contentOf(url, charsetName);
    }

    public String contentOf(URL url) {
        return URLs.contentOf(url, Charset.defaultCharset());
    }

    public List<String> linesOf(URL url) {
        return URLs.linesOf(url, Charset.defaultCharset());
    }

    public List<String> linesOf(URL url, Charset charset) {
        return URLs.linesOf(url, charset);
    }

    public List<String> linesOf(URL url, String charsetName) {
        return URLs.linesOf(url, charsetName);
    }

    public void setLenientDateParsing(boolean value) {
        AbstractDateAssert.setLenientDateParsing(value);
    }

    public void registerCustomDateFormat(DateFormat userCustomDateFormat) {
        AbstractDateAssert.registerCustomDateFormat(userCustomDateFormat);
    }

    public void registerCustomDateFormat(String userCustomDateFormatPattern) {
        AbstractDateAssert.registerCustomDateFormat(userCustomDateFormatPattern);
    }

    public void useDefaultDateFormatsOnly() {
        AbstractDateAssert.useDefaultDateFormatsOnly();
    }

    public <T> T assertThat(final AssertProvider<T> component) {
        return AssertionsForInterfaceTypes.assertThat(component);
    }

    public AbstractCharSequenceAssert<?, ? extends CharSequence> assertThat(CharSequence actual) {
        return AssertionsForInterfaceTypes.assertThat(actual);
    }

    public AbstractCharSequenceAssert<?, ? extends CharSequence> assertThat(StringBuilder actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractCharSequenceAssert<?, ? extends CharSequence> assertThat(StringBuffer actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public AbstractStringAssert<?> assertThat(String actual) {
        return AssertionsForClassTypes.assertThat(actual);
    }

    public <ELEMENT> IterableAssert<ELEMENT> assertThat(Iterable<? extends ELEMENT> actual) {
        return AssertionsForInterfaceTypes.assertThat(actual);
    }

    public <ELEMENT> IterableAssert<ELEMENT> assertThatIterable(Iterable<? extends ELEMENT> actual) {
        return assertThat(actual);
    }

    public <ELEMENT> IteratorAssert<ELEMENT> assertThat(Iterator<? extends ELEMENT> actual) {
        return AssertionsForInterfaceTypes.assertThat(actual);
    }

    public <ELEMENT> IteratorAssert<ELEMENT> assertThatIterator(Iterator<? extends ELEMENT> actual) {
        return assertThat(actual);
    }

    public <E> AbstractCollectionAssert<?, Collection<? extends E>, E, ObjectAssert<E>> assertThat(Collection<? extends E> actual) {
        return AssertionsForInterfaceTypes.assertThat(actual);
    }

    public <E> AbstractCollectionAssert<?, Collection<? extends E>, E, ObjectAssert<E>> assertThatCollection(Collection<? extends E> actual) {
        return assertThat(actual);
    }

    public <ELEMENT> ListAssert<ELEMENT> assertThat(List<? extends ELEMENT> actual) {
        return AssertionsForInterfaceTypes.assertThat(actual);
    }

    public <ELEMENT> ListAssert<ELEMENT> assertThatList(List<? extends ELEMENT> actual) {
        return assertThat(actual);
    }

    public <ELEMENT> ListAssert<ELEMENT> assertThat(Stream<? extends ELEMENT> actual) {
        return AssertionsForInterfaceTypes.assertThat(actual);
    }

    public <ELEMENT> ListAssert<ELEMENT> assertThatStream(Stream<? extends ELEMENT> actual) {
        return assertThat(actual);
    }

    public ListAssert<Double> assertThat(DoubleStream actual) {
        return AssertionsForInterfaceTypes.assertThat(actual);
    }

    public ListAssert<Long> assertThat(LongStream actual) {
        return AssertionsForInterfaceTypes.assertThat(actual);
    }

    public ListAssert<Integer> assertThat(IntStream actual) {
        return AssertionsForInterfaceTypes.assertThat(actual);
    }

    public <ELEMENT> SpliteratorAssert<ELEMENT> assertThat(Spliterator<ELEMENT> actual) {
        return AssertionsForInterfaceTypes.assertThat(actual);
    }

    public AbstractPathAssert<?> assertThat(Path actual) {
        return AssertionsForInterfaceTypes.assertThat(actual);
    }

    public AbstractPathAssert<?> assertThatPath(Path actual) {
        return assertThat(actual);
    }

    public <K, V> MapAssert<K, V> assertThat(Map<K, V> actual) {
        return AssertionsForInterfaceTypes.assertThat(actual);
    }

    public <T extends Comparable<? super T>> AbstractComparableAssert<?, T> assertThat(T actual) {
        return AssertionsForInterfaceTypes.assertThat(actual);
    }

    public <T> AbstractUniversalComparableAssert<?, T> assertThatComparable(Comparable<T> actual) {
        return AssertionsForInterfaceTypes.assertThatComparable(actual);
    }

    public <T extends AssertDelegateTarget> T assertThat(T assertion) {
        return assertion;
    }

    public void useRepresentation(Representation customRepresentation) {
        AbstractAssert.setCustomRepresentation(customRepresentation);
    }

    public <T> void registerFormatterForType(Class<T> type, Function<T, String> formatter) {
        StandardRepresentation.registerFormatterForType(type, formatter);
    }

    public void useDefaultRepresentation() {
        AbstractAssert.setCustomRepresentation(ConfigurationProvider.CONFIGURATION_PROVIDER.representation());
    }

    static {
        ConfigurationProvider.class.hashCode();
    }
}