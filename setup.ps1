# Create necessary directories
New-Item -ItemType Directory -Force -Path "lib"
New-Item -ItemType Directory -Force -Path ".jdk-temp"

# Download JDK 17
Write-Host "Downloading OpenJDK 17..."
$jdkUrl = "https://api.adoptium.net/v3/binary/latest/17/ga/windows/x64/jdk/hotspot/normal/eclipse"
Invoke-WebRequest -Uri $jdkUrl -OutFile "jdk.zip"

# Extract JDK
Write-Host "Extracting OpenJDK 17..."
Expand-Archive -Path "jdk.zip" -DestinationPath ".jdk-temp"

# Move JDK contents to .jdk
$extractedFolder = Get-ChildItem -Path ".jdk-temp" | Select-Object -First 1
Move-Item -Path $extractedFolder.FullName -Destination ".jdk"

# Clean up temporary JDK files
Remove-Item -Force "jdk.zip"
Remove-Item -Recurse -Force ".jdk-temp"

# Download JUnit and Hamcrest
Write-Host "Downloading JUnit 4.13.2..."
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar" -OutFile "lib/junit-4.13.2.jar"

Write-Host "Downloading Hamcrest Core 1.3..."
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar" -OutFile "lib/hamcrest-core-1.3.jar"

Write-Host "Environment setup complete!"
