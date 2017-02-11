'use strict';

angular.module('stepApp')
    .factory('InstituteBuilding', function ($resource, DateUtils) {
        return $resource('api/instituteBuildings/:id', {}, {
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
    .factory('InstituteBuildingByInstitute', function ($resource) {
        return $resource('api/InstituteBuildingByInstituteId/:id', {}, {
            'get': { method: 'get', isArray: false}
        });
    });
