using Confluent.Kafka;
using Confluent.Kafka.Serialization;
using System;
using System.Collections.Generic;
using System.Text;

namespace SimpleProducer
{
    /*
     * Playing with Kafka. Simple producer. 
     */
    class Program
    {
        static void Main(string[] args)
        {
            string brokerList = "localhost:9092";
            string topicName = "testTopic";

            var config = new Dictionary<string, object> { { "bootstrap.servers", brokerList } };

            using (var producer = new Producer<Null, string>(config, null, new StringSerializer(Encoding.UTF8)))
            {
                Console.WriteLine($"{producer.Name} producing on {topicName}. q to exit.");

                string text;
                while ((text = Console.ReadLine()) != "q")
                {
                    try
                    {
                        var deliveryReport = producer.ProduceAsync(topicName, null, text);
                        var result = deliveryReport.ContinueWith(task =>
                        {
                            Console.WriteLine($"Partition: {task.Result.Partition}, Offset: {task.Result.Offset}");
                        })
                        .Wait(3000);
                        if (!result)
                        {
                            Console.WriteLine("Occurred timeout");
                        }
                    }
                    catch (Exception e)
                    {
                        Console.WriteLine($"Error during sending message: {e.Message}");
                    }
                }

                // Tasks are not waited on synchronously (ContinueWith is not synchronous),
                // so it's possible they may still in progress here.
                producer.Flush(TimeSpan.FromSeconds(10));
            }

        }
    }
}
