#!/bin/bash

# Wait for SQL Server to start
echo "Waiting for SQL Server to start..."
sleep 20s

# Install sqlcmd if not installed
echo "Installing sqlcmd..."
/opt/mssql/bin/mssql-conf set sqlagent.enabled true
apt-get update && apt-get install -y curl unixodbc unixodbc-dev gnupg2
curl https://packages.microsoft.com/keys/microsoft.asc | apt-key add -
curl https://packages.microsoft.com/config/ubuntu/20.04/prod.list > /etc/apt/sources.list.d/mssql-release.list
apt-get update
ACCEPT_EULA=Y apt-get install -y msodbcsql17 mssql-tools
echo 'export PATH="$PATH:/opt/mssql-tools/bin"' >> ~/.bashrc
source ~/.bashrc

# Create database
echo "Creating database WorkFlowX..."
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P 'Khai@201005' -Q "IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'WorkFlowX') CREATE DATABASE WorkFlowX"

echo "Database WorkFlowX created successfully!"

# Restart SQL Server
echo "Restarting SQL Server..."
/opt/mssql/bin/sqlservr
