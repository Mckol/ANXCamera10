package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTakeUntil<T, U> extends AbstractFlowableWithUpstream<T, T> {
    final Publisher<? extends U> other;

    /* access modifiers changed from: package-private */
    public static final class TakeUntilMainSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -4945480365982832967L;
        final Subscriber<? super T> actual;
        final AtomicThrowable error = new AtomicThrowable();
        final TakeUntilMainSubscriber<T>.OtherSubscriber other = new OtherSubscriber();
        final AtomicLong requested = new AtomicLong();
        final AtomicReference<Subscription> s = new AtomicReference<>();

        final class OtherSubscriber extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
            private static final long serialVersionUID = -3592821756711087922L;

            OtherSubscriber() {
            }

            @Override // org.reactivestreams.Subscriber
            public void onComplete() {
                SubscriptionHelper.cancel(TakeUntilMainSubscriber.this.s);
                TakeUntilMainSubscriber takeUntilMainSubscriber = TakeUntilMainSubscriber.this;
                HalfSerializer.onComplete(takeUntilMainSubscriber.actual, takeUntilMainSubscriber, takeUntilMainSubscriber.error);
            }

            @Override // org.reactivestreams.Subscriber
            public void onError(Throwable th) {
                SubscriptionHelper.cancel(TakeUntilMainSubscriber.this.s);
                TakeUntilMainSubscriber takeUntilMainSubscriber = TakeUntilMainSubscriber.this;
                HalfSerializer.onError(takeUntilMainSubscriber.actual, th, takeUntilMainSubscriber, takeUntilMainSubscriber.error);
            }

            @Override // org.reactivestreams.Subscriber
            public void onNext(Object obj) {
                SubscriptionHelper.cancel(this);
                onComplete();
            }

            @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
            public void onSubscribe(Subscription subscription) {
                if (SubscriptionHelper.setOnce(this, subscription)) {
                    subscription.request(Long.MAX_VALUE);
                }
            }
        }

        TakeUntilMainSubscriber(Subscriber<? super T> subscriber) {
            this.actual = subscriber;
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            SubscriptionHelper.cancel(this.s);
            SubscriptionHelper.cancel(this.other);
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            SubscriptionHelper.cancel(this.other);
            HalfSerializer.onComplete(this.actual, this, this.error);
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            SubscriptionHelper.cancel(this.other);
            HalfSerializer.onError(this.actual, th, this, this.error);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(T t) {
            HalfSerializer.onNext(this.actual, t, this, this.error);
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            SubscriptionHelper.deferredSetOnce(this.s, this.requested, subscription);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            SubscriptionHelper.deferredRequest(this.s, this.requested, j);
        }
    }

    public FlowableTakeUntil(Flowable<T> flowable, Publisher<? extends U> publisher) {
        super(flowable);
        this.other = publisher;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Flowable
    public void subscribeActual(Subscriber<? super T> subscriber) {
        TakeUntilMainSubscriber takeUntilMainSubscriber = new TakeUntilMainSubscriber(subscriber);
        subscriber.onSubscribe(takeUntilMainSubscriber);
        this.other.subscribe(takeUntilMainSubscriber.other);
        this.source.subscribe((FlowableSubscriber) takeUntilMainSubscriber);
    }
}
