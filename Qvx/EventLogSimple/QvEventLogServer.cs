using QlikView.Qvx.QvxLibrary;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Webpals.QvEventLogSimple
{
    internal class QvEventLogServer : QvxServer
    {
        public override QvxConnection CreateConnection()
        {
            Console.WriteLine("CreateConnection()");
            return new QvEventLogConnection();
        }

        public override string CreateConnectionString()
        {
            Console.WriteLine("CreateConnectionString()");
            QvxLog.Log(QvxLogFacility.Application, QvxLogSeverity.Debug, "CreateConnectionString()");
            return "Server=localhost";
        }
    }
}
