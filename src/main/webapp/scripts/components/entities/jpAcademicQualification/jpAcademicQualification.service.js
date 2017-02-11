'use strict';

angular.module('stepApp')
    .factory('JpAcademicQualification', function ($resource, DateUtils) {
        return $resource('api/jpAcademicQualifications/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if(data){
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
