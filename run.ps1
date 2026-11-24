$ErrorActionPreference = "Stop"

$envFile = Join-Path $PSScriptRoot ".env"
if (-not (Test-Path $envFile)) {
	Write-Error "File .env tidak ditemukan. Salin .env.example menjadi .env lalu isi kredensial Nararouter."
	exit 1
}

Get-Content $envFile | ForEach-Object {
	$line = $_.Trim()
	if ($line -and -not $line.StartsWith("#")) {
		$parts = $line -split "=", 2
		if ($parts.Count -eq 2) {
			$name = $parts[0].Trim()
			$value = $parts[1].Trim().Trim('"').Trim("'")
			[Environment]::SetEnvironmentVariable($name, $value, "Process")
		}
	}
}

if (-not $env:NARAROUTER_API_KEY -or -not $env:NARAROUTER_BASE_URL) {
	Write-Error "Isi NARAROUTER_API_KEY dan NARAROUTER_BASE_URL di file .env."
	exit 1
}

Push-Location $PSScriptRoot
try {
	& .\gradlew.bat bootRun
	exit $LASTEXITCODE
}
finally {
	Pop-Location
}
