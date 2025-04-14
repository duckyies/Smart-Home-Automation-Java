const apiUrl = 'http://localhost:8080/devices';
let powerConsumptionChart, deviceTypeChart, deviceLocationChart;

function showPage(pageId) {
    document.querySelectorAll('.page').forEach(page => {
        page.style.display = 'none';
    });

    const pageElement = document.getElementById(pageId);
    if (pageElement) {
        pageElement.style.display = 'block';
    } else {
        console.error(`Page with ID ${pageId} not found.`);
        document.getElementById('page1').style.display = 'block';
    }

    if (pageId === 'page2') {
        updateCharts();
    }
    if (pageId === 'page3') {
        fetchAndDisplayDataStructures();

    }
}

function initializeCharts() {
    if (powerConsumptionChart) {
        powerConsumptionChart.destroy();
    }
    if (deviceTypeChart) {
        deviceTypeChart.destroy();
    }
    if (deviceLocationChart) {
        deviceLocationChart.destroy();
    }


    const powerCtx = document.getElementById('powerConsumptionChart').getContext('2d');
    powerConsumptionChart = new Chart(powerCtx, {
        type: 'bar',
        data: {
            labels: [],
            datasets: [{
                label: 'Power Consumption (W)',
                data: [],
                backgroundColor: 'rgba(255, 99, 132, 0.5)',
                borderColor: 'rgba(255, 99, 132, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            },
            responsive: true,
            maintainAspectRatio: false
        }
    });


    const typeCtx = document.getElementById('deviceTypeChart').getContext('2d');
    deviceTypeChart = new Chart(typeCtx, {
        type: 'pie',
        data: {
            labels: [],
            datasets: [{
                label: 'Number of Devices',
                data: [],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.5)', 'rgba(54, 162, 235, 0.5)',
                    'rgba(255, 206, 86, 0.5)', 'rgba(75, 192, 192, 0.5)',
                    'rgba(153, 102, 255, 0.5)', 'rgba(255, 159, 64, 0.5)',
                    'rgba(199, 199, 199, 0.5)', 'rgba(83, 102, 255, 0.5)',
                    'rgba(100, 255, 100, 0.5)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)', 'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)', 'rgba(75, 192, 192, 1)',
                    'rgba(153, 102, 255, 1)', 'rgba(255, 159, 64, 1)',
                    'rgba(199, 199, 199, 1)', 'rgba(83, 102, 255, 1)',
                    'rgba(100, 255, 100, 1)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false
        }
    });

    const locationCtx = document.getElementById('deviceLocationChart').getContext('2d');
    deviceLocationChart = new Chart(locationCtx, {
        type: 'doughnut',
        data: {
            labels: [],
            datasets: [{
                label: 'Number of Devices',
                data: [],
                backgroundColor: [],
                borderColor: [],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false
        }
    });
}

async function updateCharts() {
    if (!powerConsumptionChart || !deviceTypeChart || !deviceLocationChart) {
        console.warn("Charts not initialized yet. Initializing now.");
        initializeCharts();

    }
    try {
        const devicesResponse = await fetch(apiUrl);
        if (!devicesResponse.ok) {
            throw new Error(`HTTP error! status: ${devicesResponse.status}`);
        }
        const devices = await devicesResponse.json();

        // --- Power Consumption Chart Update ---
        const powerData = devices.map(device =>
            (device.base_power_consumption || 0) * (device.power_level > 0 ? device.power_level : (device.is_turned_on ? 1 : 0)) // Simple example logic
        );
        const powerLabels = devices.map(device => device.device_name || 'Unnamed Device');

        if (powerConsumptionChart) {
            powerConsumptionChart.data.labels = powerLabels;
            powerConsumptionChart.data.datasets[0].data = powerData;
            powerConsumptionChart.update();
        }


        // --- Device Type Chart Update ---
        const typeCounts = {};
        devices.forEach(device => {
            const typeKey = device.device_type || 'Unknown Type';
            typeCounts[typeKey] = (typeCounts[typeKey] || 0) + 1;
        });

        if (deviceTypeChart) {
            deviceTypeChart.data.labels = Object.keys(typeCounts);
            deviceTypeChart.data.datasets[0].data = Object.values(typeCounts);
            // Ensure enough colors are available or cycle through them
            const requiredColors = Object.keys(typeCounts).length;
            const bgColors = deviceTypeChart.data.datasets[0].backgroundColor;
            const borderColors = deviceTypeChart.data.datasets[0].borderColor;
            while (bgColors.length < requiredColors) {
                bgColors.push(`rgba(${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, 0.5)`);
                borderColors.push(bgColors[bgColors.length - 1].replace('0.5', '1'));
            }
            deviceTypeChart.update();
        }

        // --- Device Location Chart Update ---
        const locationCounts = {};
        devices.forEach(device => {
            const locKey = device.location || 'Unknown Location';
            locationCounts[locKey] = (locationCounts[locKey] || 0) + 1;
        });

        const locationLabels = Object.keys(locationCounts);
        const locationData = Object.values(locationCounts);

        if (deviceLocationChart) {
            deviceLocationChart.data.labels = locationLabels;
            deviceLocationChart.data.datasets[0].data = locationData;

            // Dynamic color generation for locations
            const backgroundColors = [];
            const borderColors = [];
            for (let i = 0; i < locationLabels.length; i++) {
                const color = `rgba(${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, 0.7)`; // Slightly less transparent
                const borderColor = color.replace('0.7', '1');
                backgroundColors.push(color);
                borderColors.push(borderColor);
            }
            deviceLocationChart.data.datasets[0].backgroundColor = backgroundColors;
            deviceLocationChart.data.datasets[0].borderColor = borderColors;
            deviceLocationChart.update();
        }

    } catch (error) {
        console.error('Error updating charts:', error);
        // Optionally display an error message to the user on the page
    }
}


// --- Device List and Controls ---
async function fetchDevices() {
    try {
        const response = await fetch(apiUrl);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const devices = await response.json();

        const poweredOnContainer = document.getElementById('powered-on-devices');
        const poweredOffContainer = document.getElementById('powered-off-devices');
        const removeDeviceSelect = document.getElementById('device-select'); // For remove dropdown

        // Clear previous content safely
        poweredOnContainer.innerHTML = '';
        poweredOffContainer.innerHTML = '';
        removeDeviceSelect.innerHTML = ''; // Clear remove dropdown

        // Add a default option to the remove dropdown
        const defaultOption = document.createElement('option');
        defaultOption.value = "";
        defaultOption.textContent = "-- Select Device to Remove --";
        defaultOption.disabled = true; // Cannot select this
        defaultOption.selected = true; // Selected by default
        removeDeviceSelect.appendChild(defaultOption);


        devices.forEach(device => {
            const deviceElement = document.createElement('div');
            deviceElement.className = 'device';
            // Use unique ID for the element if needed elsewhere
            deviceElement.id = `device-${device.device_id}`;

            // Use snake_case keys from Python JSON
            deviceElement.innerHTML = `
                <div class="device-info">
                    <p><strong>Name:</strong> ${device.device_name || 'N/A'}</p>
                    <p><strong>Type:</strong> ${device.device_type || 'N/A'}</p>
                    <p><strong>Location:</strong> ${device.location || 'N/A'}</p>
                    <p><strong>Power Consumption:</strong> ${(device.base_power_consumption || 0).toFixed(2)} W</p>
                    <p><strong>Power Level:</strong> ${device.power_level !== undefined ? device.power_level : 'N/A'}</p>
                    <p><strong>Battery Level:</strong> ${device.battery_level !== undefined ? device.battery_level.toFixed(1) + '%' : 'N/A'}</p>
                </div>
                <div class="device-controls">
                    <button class="${device.is_turned_on ? 'off' : 'on'}" onclick="toggleDevice(${device.device_id}, ${device.is_turned_on})">
                        Turn ${device.is_turned_on ? 'Off' : 'On'}
                    </button>
                </div>
            `;

            deviceElement.addEventListener('click', (event) => {

                if (event.target.tagName !== 'BUTTON') {
                    fetchAndDisplayDeviceDetails(device.device_id);
                    showPage('page3');
                }
            });


            if (device.is_turned_on) {
                poweredOnContainer.appendChild(deviceElement);
            } else {
                poweredOffContainer.appendChild(deviceElement);
            }

            const option = document.createElement('option');
            option.value = device.device_id;
            option.textContent = `${device.device_name || 'Unnamed'} (${device.location || 'No Location'})`;
            removeDeviceSelect.appendChild(option);
        });

    } catch (error) {
        console.error('Error fetching devices:', error);
    }
}

async function toggleDevice(id, isCurrentlyOn) {
    console.log(`Toggling device ${id}. Currently on: ${isCurrentlyOn}`);
    const action = isCurrentlyOn ? 'off' : 'on';
    const endpoint = `${apiUrl}/id/${id}/${action}`;
    try {
        const response = await fetch(endpoint, { method: 'PUT' });
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(`Failed to toggle device: ${errorData.error || response.statusText}`);
        }
        console.log(`Device ${id} toggled ${action} successfully.`);
        fetchDevices();
    } catch (error) {
        console.error('Error toggling device:', error);
        alert(`Error toggling device: ${error.message}`);
    }
}


document.getElementById('add-device-form').addEventListener('submit', async (e) => {
    e.preventDefault();

    const device_name = document.getElementById('device-name').value;
    const device_type = document.getElementById('device-type').value;
    const device_group = document.getElementById('device-group').value;
    const location = document.getElementById('device-location').value;
    const base_power_consumption = parseFloat(document.getElementById('device-power-consumption').value);
    const power_level = parseInt(document.getElementById('device-power-level').value, 10);
    const max_battery_capacity = parseInt(document.getElementById('device-max-battery-capacity').value, 10);

    const is_turned_on = false;
    const battery_level = 100.0;
    const current_battery_capacity = max_battery_capacity;
    const is_on_battery = false;
    const is_interacted = false;
    const turned_on_time = null;

    const newDevicePayload = {
        device_name,
        device_type,
        device_group,
        location,
        is_turned_on,
        battery_level,
        base_power_consumption,
        max_battery_capacity,
        current_battery_capacity,
        power_level,
        is_on_battery,
        is_interacted
    };

    console.log("Sending new device payload:", newDevicePayload); // Debug log

    try {
        const response = await fetch(apiUrl, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(newDevicePayload)
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(`Failed to add device: ${errorData.error || response.statusText}`);
        }

        const result = await response.json();
        console.log("Device added:", result);

        fetchDevices();
        updateCharts();
        e.target.reset();
        alert('Device added successfully!');

    } catch (error) {
        console.error('Error adding device:', error);
        alert(`Error adding device: ${error.message}`); // Show specific error
    }
});

document.getElementById('remove-device-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    const deviceId = document.getElementById('device-select').value;
    if (!deviceId) {
        alert("Please select a device to remove.");
        return;
    }
    if (confirm(`Are you sure you want to remove the selected device (ID: ${deviceId})?`)) {
        removeDevice(deviceId);
    }
});

async function removeDevice(deviceId) {
    try {
        const response = await fetch(`${apiUrl}/id/${deviceId}`, { method: 'DELETE' });
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(`Failed to remove device: ${errorData.error || response.statusText}`);
        }
        console.log(`Device ${deviceId} removed successfully.`);
        fetchDevices();
        updateCharts();
        alert('Device removed successfully!');
    } catch (error) {
        console.error('Error removing device:', error);
        alert(`Error removing device: ${error.message}`);
    }
}


async function populateSelectWithOptions(selectElementId, endpoint, placeholder) {
    const selectElement = document.getElementById(selectElementId);
    if (!selectElement) {
        console.error(`Select element ${selectElementId} not found`);
        return;
    }
    selectElement.innerHTML = '';

    if (placeholder) {
        const placeholderOption = document.createElement('option');
        placeholderOption.value = "";
        placeholderOption.textContent = placeholder;
        placeholderOption.disabled = true;
        placeholderOption.selected = true;
        selectElement.appendChild(placeholderOption);
    }

    try {
        const response = await fetch(endpoint);
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        const optionsArray = await response.json();

        if (Array.isArray(optionsArray)) {
            optionsArray.forEach(optionValue => {
                const optionElement = document.createElement('option');
                optionElement.value = optionValue;
                optionElement.textContent = optionValue;
                selectElement.appendChild(optionElement);
            });
        } else {
            console.error(`Expected an array from ${endpoint}, but received:`, optionsArray);
        }
    } catch (error) {
        console.error(`Error fetching options for ${selectElementId} from ${endpoint}:`, error);
        const errorOption = document.createElement('option');
        errorOption.value = "";
        errorOption.textContent = "Error loading options";
        errorOption.disabled = true;
        selectElement.appendChild(errorOption);
    }
}


async function fetchAndPopulateAllSelects() {
    // Populate Add Device Form Selects
    await populateSelectWithOptions('device-type', `${apiUrl}/types`, '-- Select Type --');
    await populateSelectWithOptions('device-group', `${apiUrl}/groups`, '-- Select Group --');
    await populateSelectWithOptions('device-location', `${apiUrl}/locations`, '-- Select Location --');

    await populateSelectWithOptions('location-add-person-select', `${apiUrl}/locations`, '-- Select Location --');
    await populateSelectWithOptions('location-remove-person-select', `${apiUrl}/locations`, '-- Select Location --');
}



async function addLocation(locationName) {
    if (!locationName || locationName.trim() === '') {
        alert("Please enter a valid location name.");
        return;
    }
    try {
        const response = await fetch(`${apiUrl}/locations`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ location_name: locationName })
        });
        if (response.ok) {
            await fetchAndPopulateAllSelects(); // Repopulate selects
            alert('Location added successfully!');
        } else {
            const errorData = await response.json();
            alert(`Failed to add location: ${errorData.error || response.statusText}`);
        }
    } catch (error) {
        console.error('Error adding location:', error);
        alert('Error adding location.');
    }
}

async function removeLocation(locationName) {
    console.warn("Attempting to remove location - ensure DELETE /locations/{name} endpoint exists.");
    if (!locationName) {
        alert("Please select a location to remove.");
        return;
    }
    if (!confirm(`Are you sure you want to remove location: ${locationName}?`)) {
        return;
    }
    try {
        const response = await fetch(`${apiUrl}/locations/${locationName}`, { method: 'DELETE' });
        if (response.ok) {
            await fetchAndPopulateAllSelects(); // Repopulate selects
            alert('Location removed successfully!');
        } else {
            const errorData = await response.json();
            alert(`Failed to remove location: ${errorData.error || response.statusText}`);
        }
    } catch (error) {
        console.error('Error removing location:', error);
        alert('Error removing location.');
    }
}

document.getElementById('remove-location-form')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    const locationName = document.getElementById('location-remove-select')?.value; // Use optional chaining
    if (locationName) {
        removeLocation(locationName);
    } else {
        alert("Please select a location.");
    }
});

document.getElementById('add-location-form')?.addEventListener('submit', async (e) => {
    e.preventDefault();
    const locationName = document.getElementById('new-location-name')?.value; // Assuming input ID
    if (locationName) {
        addLocation(locationName);
        e.target.reset(); // Clear the input
    } else {
        alert("Please enter a location name.");
    }
});


// --- Person Management ---
async function addPersonToLocation(locationName) {
    if (!locationName) {
        alert("Please select a location.");
        return;
    }
    try {
        const response = await fetch(`${apiUrl}/location/${encodeURIComponent(locationName)}/add_person`, { method: 'PUT' });
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.error || response.statusText);
        }
        alert(`Person added to location '${locationName}' successfully!`);
    } catch (error) {
        console.error('Error adding person to location:', error);
        alert(`Error adding person: ${error.message}`);
    }
}

async function removePersonFromLocation(locationName) {
    if (!locationName) {
        alert("Please select a location.");
        return;
    }
    try {
        const response = await fetch(`${apiUrl}/location/${encodeURIComponent(locationName)}/remove_person`, { method: 'PUT' });
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.error || response.statusText);
        }
        alert(`Person removed from location '${locationName}' successfully!`);
    } catch (error) {
        console.error('Error removing person from location:', error);
        alert(`Error removing person: ${error.message}`);
    }
}

// Event listeners for Person forms
document.getElementById('add-person-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    const locationName = document.getElementById('location-add-person-select').value;
    addPersonToLocation(locationName);
});

document.getElementById('remove-person-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    const locationName = document.getElementById('location-remove-person-select').value;
    removePersonFromLocation(locationName);
});


// --- Log Boxes ---
async function updateLogBoxes() {
    const logUrls = [
        { id: 'info-logs', url: '/log/info' },
        { id: 'warning-logs', url: '/log/warning' },
        { id: 'error-logs', url: '/log/severe' },
        { id: 'debug-logs', url: '/log/battery' },
        { id: 'verbose-logs', url: '/log/power_consumption' },
    ];

    for (const log of logUrls) {
        try {
            const response = await fetch(apiUrl + log.url);
            const logText = (await response.text()).replaceAll('"', '').replaceAll("[", "").replaceAll("]", "");
            const logBox = document.getElementById(log.id);
            if (logText.trim() !== "") logBox.textContent += logText + '\n';

            logBox.parentNode.scrollTop = logBox.parentNode.scrollHeight;

        } catch (error) {
            console.error(`Error fetching ${log.id}:`, error);
        }
    }
}


async function fetchAndDisplayDeviceDetails(deviceId) {
    console.log(`Fetching details for device ID: ${deviceId}`);
    try {
        const response = await fetch(`${apiUrl}/id/${deviceId}`);
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(`Failed to fetch device details: ${errorData.error || response.statusText}`);
        }
        const device = await response.json(); // Expecting snake_case keys

        const deviceDetailsContainer = document.getElementById('device-details');
        if (!deviceDetailsContainer) return;

        deviceDetailsContainer.innerHTML = `
            <h3>Device Details: ${device.device_name || 'N/A'}</h3>
            <p><strong>ID:</strong> ${device.device_id !== undefined ? device.device_id : 'N/A'}</p>
            <p><strong>Type:</strong> ${device.device_type || 'N/A'}</p>
            <p><strong>Group:</strong> ${device.device_group || 'N/A'}</p>
            <p><strong>Location:</strong> ${device.location || 'N/A'}</p>
            <p><strong>Status:</strong> ${device.is_turned_on ? 'On' : 'Off'}</p>
            <p><strong>Power Consumption:</strong> ${device.base_power_consumption !== undefined ? device.base_power_consumption.toFixed(2) + ' W' : 'N/A'}</p>
            <p><strong>Power Level:</strong> ${device.power_level !== undefined ? device.power_level : 'N/A'}</p>
            <p><strong>Battery Level:</strong> ${device.battery_level !== undefined ? device.battery_level.toFixed(1) + '%' : 'N/A'}</p>
            <p><strong>Max Battery Capacity:</strong> ${device.max_battery_capacity !== undefined ? device.max_battery_capacity : 'N/A'} units</p> <!-- Adjust units if needed -->
            <p><strong>Current Battery Capacity:</strong> ${device.current_battery_capacity !== undefined ? device.current_battery_capacity.toFixed(1) : 'N/A'} units</p> <!-- Adjust units -->
            <p><strong>On Battery:</strong> ${device.is_on_battery ? 'Yes' : 'No'}</p>
            <p><strong>Interacted:</strong> ${device.is_interacted ? 'Yes' : 'No'}</p>
            <p><strong>Turned On Since:</strong> ${device.turned_on_time ? new Date(device.turned_on_time * 1000).toLocaleString() : (device.is_turned_on ? 'Unknown' : 'N/A')}</p>
            <p><strong>Minutes Since Turned On:</strong> ${device.minutes_since_turned_on !== undefined ? device.minutes_since_turned_on : 'N/A'}</p>
            `;

    } catch (error) {
        console.error('Error fetching device details:', error);
        const deviceDetailsContainer = document.getElementById('device-details');
        if (deviceDetailsContainer) {
            deviceDetailsContainer.innerHTML = `<p class="error">Error loading details: ${error.message}</p>`;
        }
    }
}

function formatDataStructureContent(data, name) {
    let content = `<h4>${name}</h4>`;
    if (!data || (Array.isArray(data) && data.length === 0)) {
        content += "<p><em>(Empty)</em></p>";
    } else if (Array.isArray(data)) {
        content += "<ul>";
        data.forEach(item => {
            let itemString;
            try {
                itemString = JSON.stringify(item);
                if (itemString.length > 150) {
                    itemString = itemString.substring(0, 150) + "... }";
                }
            } catch {
                itemString = String(item);
            }
            content += `<li>${itemString}</li>`;
        });
        content += "</ul>";
    } else {
        content += `<pre>${JSON.stringify(data, null, 2)}</pre>`;
    }
    return content;
}

async function fetchAndDisplayDataStructures() {
    const linkedListsContainer = document.getElementById('linked-lists-info');
    const priorityQueuesContainer = document.getElementById('priority-queues-info');

    if (!linkedListsContainer || !priorityQueuesContainer) {
        console.warn("Debug data structure containers not found.");
        return;
    }

    try {
        const linkedListsResponse = await fetch(`${apiUrl}/debug/linkedlists`);
        if (linkedListsResponse.ok) {
            const linkedLists = await linkedListsResponse.json();
            let llContent = "";
            llContent += formatDataStructureContent(linkedLists.loggingList, "Logging List");
            llContent += formatDataStructureContent(linkedLists.powerConsumptionLogList, "Power Consumption Logs");
            llContent += formatDataStructureContent(linkedLists.deviceBatteryLogList, "Device Battery Logs");
            llContent += formatDataStructureContent(linkedLists.ruleList, "Rule List");
            linkedListsContainer.innerHTML = llContent;
        } else {
            linkedListsContainer.innerHTML = `<p class="error">Error loading Linked Lists: ${linkedListsResponse.status}</p>`;
        }

        const priorityQueuesResponse = await fetch(`${apiUrl}/debug/priorityqueues`);
        if (priorityQueuesResponse.ok) {
            const priorityQueues = await priorityQueuesResponse.json();
            let pqContent = "";
            pqContent += formatDataStructureContent(priorityQueues.deviceQueue, "Device Queue");
            pqContent += formatDataStructureContent(priorityQueues.powerReducibleDevices, "Power Reducible Devices");
            pqContent += formatDataStructureContent(priorityQueues.turnBackOnDevices, "Turn Back On Devices");
            priorityQueuesContainer.innerHTML = pqContent;
        } else {
            priorityQueuesContainer.innerHTML = `<p class="error">Error loading Priority Queues: ${priorityQueuesResponse.status}</p>`;
        }

    } catch (error) {
        console.error('Error fetching data structures:', error);
        if (linkedListsContainer) linkedListsContainer.innerHTML = `<p class="error">Failed to fetch Linked List data.</p>`;
        if (priorityQueuesContainer) priorityQueuesContainer.innerHTML = `<p class="error">Failed to fetch Priority Queue data.</p>`;
    }
}


document.addEventListener('DOMContentLoaded', () => {
    console.log("DOM fully loaded and parsed");

    initializeCharts();
    fetchDevices();
    fetchAndPopulateAllSelects();
    updateLogBoxes();
    fetchAndDisplayDataStructures();


    setInterval(updateLogBoxes, 5000);
    setInterval(fetchDevices, 1000);
    setInterval(fetchAndPopulateAllSelects, 15000);
    setInterval(fetchAndDisplayDataStructures, 5000);
    setInterval(updateCharts, 10000);

    showPage('page1');
});