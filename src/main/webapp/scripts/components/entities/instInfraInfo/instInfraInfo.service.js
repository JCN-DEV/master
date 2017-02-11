'use strict';

angular.module('stepApp')
    .factory('InstInfraInfo', function ($resource, DateUtils) {
        return $resource('api/instInfraInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    /*console.log(data);

                    data = angular.fromJson(data);
                    return data;*/
                    if(!data){
                       data={id:0};
                   }
                   return data = angular.fromJson(data);
                }
            },
            'update': { method:'PUT' }
        });
    })
    .factory('InstInfraInfoDecline', function ($resource) {
        return $resource('api/instInfraInfos/decline/:id', {}, {
            'update': { method: 'PUT', isArray: true}
        });
    })
    .factory('InstInfraBuildingDirections', function ($resource) {
        return $resource('api/instInfraBuildingDirections',{}, {
            'get': { method: 'GET',isArray: true }
        });
    });
