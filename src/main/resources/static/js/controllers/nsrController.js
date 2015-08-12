var app = angular.module('app').controller('NsrCtrl', function ($scope, $compile, $cookieStore, $routeParams, http, serviceAPI, $window, $route, $interval, $http, topologiesAPI) {

    var url = 'http://localhost:8080/api/v1/ns-records';


    loadTable();


    $scope.textTopologyJson = '';
    $scope.file = '';
    $scope.alerts = [];


    $scope.tabs = [
        {active: true},
        {active: false},
        {active: false}
    ];


    $scope.setFile = function (element) {
        $scope.$apply(function ($scope) {

            var f = element.files[0];
            if (f) {
                var r = new FileReader();
                r.onload = function (element) {
                    var contents = element.target.result;
                    $scope.file = contents;
                };
                r.readAsText(f);
            } else {
                alert("Failed to load file");
            }
        });
    };


    $scope.checkIP = function (str) {
        if (str === '')
            return true;
        else
            return /^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$|^(([a-zA-Z]|[a-zA-Z][a-zA-Z0-9\-]*[a-zA-Z0-9])\.)*([A-Za-z]|[A-Za-z][A-Za-z0-9\-]*[A-Za-z0-9])$|^\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:)))(%.+)?\s*$/.test(str);

    };


    $scope.sendFile = function () {


        $('.modal').modal('hide');
        var postNSD;
        var sendOk = true;
        var type = 'topology';
        if ($scope.file !== '') {
            postNSD = $scope.file;
            if (postNSD.charAt(0) === '<')
                type = 'definitions';
        }

        else if ($scope.textTopologyJson !== '')
            postNSD = $scope.textTopologyJson;
        else if ($scope.topology.serviceContainers.length !== 0)
            postNSD = $scope.topology;

        else {
            alert('Problem with NSD');
            sendOk = false;

        }

        console.log(postNSD);
        console.log(type);

        if (sendOk) {
            if (type === 'topology') {
                http.post(url, postNSD)
                    .success(function (response) {
                        showOk('Network Service Descriptors stored!');
                        loadTable();

                        //                        window.setTimeout($scope.cleanModal(), 3000);
                    })
                    .error(function (data, status) {
                        showError(status, data);
                    });
            }

            else {
                http.postXML('/api/rest/tosca/v2/definitions/', postNSD)
                    .success(function (response) {
                        showOk('Definition created!');
                        loadTable();
                        //                        window.setTimeout($scope.cleanModal(), 3000);
                    })
                    .error(function (data, status) {
                        showError(status, data);
                    });
            }
        }

    };


    $scope.Jsplumb = function () {

        http.syncGet(url + $routeParams.topologyid).then(function (response) {
            topologiesAPI.Jsplumb(response);
            console.log(response);

        });
    };

    $scope.isEmpty = function (obj) {
        return angular.equals({}, obj);
    };

    $scope.deleteNSD = function (data) {
        http.delete(url + data.id)
            .success(function (response) {
                showOk('Deleted Network Service Descriptor with id: ' + data.id);
                loadTable();
            })
            .error(function (data, status) {
                showError(status, data);
            });
    };


    $scope.returnUptime = function (longUptime) {
        var string = serviceAPI.returnStringUptime(longUptime);
        return string;
    };

    $scope.stringContains = function (k1, k2) {
        return k2.indexOf(k1) > -1;
    };


    $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
    };


    $scope.deleteNSR = function (data) {
        http.delete(url + '/' + data.id)
            .success(function (response) {
                showOk('Network Service Record deleted!');
                loadTable();
            })
            .error(function (data, status) {
                showError(data, status);
            });
    };


    function showError(status, data) {
        $scope.alerts.push({
            type: 'danger',
            msg: 'ERROR: <strong>HTTP status</strong>: ' + status + ' response <strong>data</strong>: ' + data
        });
//        loadTable();
        $('.modal').modal('hide');
    }

    function showOk(msg) {
        $scope.alerts.push({type: 'success', msg: msg});
        $('.modal').modal('hide');
    }


    function loadTable() {
        //if (!$('#jsonInfo').hasClass('in'))
        if (angular.isUndefined($routeParams.nsrecordId))
            http.get(url)
                .success(function (response, status) {
                    $scope.nsrecords = response;
                    console.log(response);
                })
                .error(function (data, status) {
                    showError(status, data);
                    //var destinationUrl = '#';
                    //$window.location.href = destinationUrl;
                });
        else
            http.get(url + '/' + $routeParams.nsrecordId)
                .success(function (response, status) {
                    $scope.nsrinfo = response;
                    $scope.nsrJSON = JSON.stringify(response, undefined, 4);
                    console.log(response);
                })
                .error(function (data, status) {
                    showError(status, data);
                    //var destinationUrl = '#';
                    //$window.location.href = destinationUrl;
                });
    }


});

