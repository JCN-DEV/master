'use strict';

angular.module('stepApp')
    .factory('InstituteInfraInfo', function ($resource, DateUtils) {
        return $resource('api/instituteInfraInfos/:id', {}, {
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
    .factory('InstituteInfraInfoByInstitute', function ($resource) {
        return $resource('api/instituteInfraInfoByInstituteId/:id', {}, {
            'get': { method: 'get', isArray: false}
        });
    });
