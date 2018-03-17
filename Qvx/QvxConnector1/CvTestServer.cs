using QlikView.Qvx.QvxLibrary;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Webpals.Qvx.QvxConnector1
{
    /// <summary>
    /// Test server
    /// </summary>
    internal class CvTestServer : QvxServer
    {
        public override QvxConnection CreateConnection()
        {
            return new QvTestConnection();
        }

        public override string CreateConnectionString()
        {
            QvxLog.Log(QvxLogFacility.Application, QvxLogSeverity.Debug, "CreateConnectionString()");
            return "Some connection string";
        }
    }
}
