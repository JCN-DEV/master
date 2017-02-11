'use strict';

angular.module('stepApp')
    .factory('InstituteEmpCount', function ($resource, DateUtils) {
        return $resource('api/instituteEmpCounts/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    })
    .factory('InstituteEmpCountByInstitute', function ($resource) {
        return $resource('api/instituteEmpCountByInstituteId/:id', {}, {
            'get': { method: 'get', isArray: false}
        });
    });
