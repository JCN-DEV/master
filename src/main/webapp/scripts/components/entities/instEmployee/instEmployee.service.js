'use strict';

angular.module('stepApp')
    .factory('InstEmployee', function ($resource, DateUtils) {
        return $resource('api/instEmployees/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {

                    data = angular.fromJson(data);
                    data.dob = DateUtils.convertLocaleDateFromServer(data.dob);
                    data.nid = parseInt( data.nid, 10);
                    data = data[0];
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dob = DateUtils.convertLocaleDateToServer(data.dob);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dob = DateUtils.convertLocaleDateToServer(data.dob);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('InstEmployeeApprove', function ($resource) {
        return $resource('api/instEmployees/approve/:id', {}, {
            'approve': {
                method: 'PUT',
                transformRequest: function (data) {
                    return angular.toJson(data);
                }
            }
        });
    }).factory('InstEmployeeDecline', function ($resource) {
        return $resource('api/instEmployees/decline/:id', {}, {
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    return angular.toJson(data);
                }
            }
        });
    })
    .factory('InstEmployeeEligible', function ($resource) {
        return $resource('api/instEmployees/eligibleForMpo/:id', {}, {
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    return angular.toJson(data);
                }
            }
        });
    })

    .factory('InstEmployeeByInstitute', function ($resource) {
            return $resource('api/instEmployees/findInstituteEmpByInstitute/:instituteID', {}, {
                'query': { method: 'GET', isArray: true},
            });
        });


