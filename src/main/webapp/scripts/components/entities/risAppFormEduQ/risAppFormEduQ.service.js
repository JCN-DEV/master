'use strict';

angular.module('stepApp')
    .factory('RisAppFormEduQ', function ($resource, DateUtils) {
        return $resource('api/risAppFormEduQs/:id', {}, {
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
    .factory('GetEducations', function ($resource) {
        return $resource('api/risAppFormEduQs/getEducations/:id', {}, {
            'query': {method: 'GET', isArray: true}
        });
    })



;
