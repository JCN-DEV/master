'use strict';

angular.module('stepApp')
    .factory('InstBuilding', function ($resource, DateUtils) {
        return $resource('api/instBuildings/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                   /* data = angular.fromJson(data);
                    return data;*/
                    if(!data){
                       data={id:0};
                   }
                   return data = angular.fromJson(data);
                }
            },
            'update': { method:'PUT' }
        });
    });
