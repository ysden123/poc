using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Webpals.QvEventLogSimple
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main(string[] args)
        {
            Console.WriteLine("==>Main");
            if (args != null && args.Length >= 2)
            {
                Console.WriteLine("Running QvEventLogServer...");
                var server = new QvEventLogServer();
                server.Run(args[0], args[1]);
                server.
            }
        }
    }
}
