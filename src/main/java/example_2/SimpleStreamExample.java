package example_2;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.model.ws.Message;
import akka.stream.javadsl.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CompletionStage;


public class SimpleStreamExample {



    //private CompletionStage<boolean> asyncWriteDB

    public static void run() throws IOException {
        var system = ActorSystem.create();
        final Source<Integer, NotUsed> source = Source.from(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        /*
        source
                .map(x -> x * 2)
                .fold(0, (acc, next) -> acc + next)
                .runWith(Sink.foreach(x -> System.out.println(x)), system);


        // connect the Source to the Sink, obtaining a RunnableGraph
        final Sink<Integer, CompletionStage<Integer>> sink =
                Sink.<Integer, Integer>fold(0, (aggr, next) -> aggr + next);
        final RunnableGraph<CompletionStage<Integer>> runnable =
                Source.from(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)).toMat(sink, Keep.right());

        // get the materialized value of the FoldSink
        final CompletionStage<Integer> sum1 = runnable.run(system);
        final CompletionStage<Integer> sum2 = runnable.run(system);

        sum1.whenComplete((integer, throwable) -> System.out.println(integer));
        sum2.whenComplete((integer, throwable) -> System.out.println(integer));
         */

        Flow<Integer, Integer, NotUsed> triple = Flow.of(Integer.class).map(x -> x * 2);
        Flow<Integer, Integer, NotUsed> sum = Flow.of(Integer.class).fold(0,(acc, next) -> acc + next);
        Flow<Integer, Integer, NotUsed> l = Flow.of(Integer.class).log("Logging Flow", x -> x);

        Flow<Message, String, NotUsed> combineMessageStreamIntoString = Flow.of(Message.class)
                .flatMapConcat((Message m) ->
                        m.asTextMessage()
                         .getStreamedText()
                         .reduce((acc, next) -> acc +next));

        Flow<Integer, Integer, NotUsed> unnecessary = Flow.of(Integer.class).map(x -> {
            System.out.println(x);
            return x;
        });

        //CompletionStage<Response> test =  Futures.successful(new Response(true));
        //CompletionStage<Response> insert = (CompletionStage<Response>) Futures.successful(new Response(true));
        //Flow.of(String.class).mapAsync(1,x -> insert );

        /*
        combine Message
        Parse Message to Location
        Split-Stream:
            Ask Incident Manager for Updates (?)
                Ask Route Calculator
                Respond
            Bundle 200 Updates or 120 seconds
                Write to DB
                POST to Backend Dashboard Server
                Log Event
         */

        Sink<Object, CompletionStage<Done>> log = Sink.foreach(x -> System.out.println(x));

        source
                .via(triple)
                .via(sum)
                .via(unnecessary)
                .runWith(Sink.ignore(), system);

        final Source<Integer, NotUsed> s1 = Source.range(1, 100);
        final Source<Integer, NotUsed> s2 = Source.range(100, 0);

        s1.zipWith(s2, (x,y) -> x + ":" + y).take(50).runForeach(s -> System.out.println(s), system);

        System.in.read();
        system.terminate();
    }
}
