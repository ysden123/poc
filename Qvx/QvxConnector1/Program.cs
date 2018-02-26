using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Webpals.Qvx.QvxConnector1
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("==>Main");
            //new CvTestServer().Run("parentString", "pipeName");
            new CvTestServer().RunStandalone("parentString", "pipeName", "connectString", "targetPath", "standaloneQury");
            Console.WriteLine("<==Main");
        }
    }
}
