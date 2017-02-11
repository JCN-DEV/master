'use strict';

angular.module('stepApp')
    .factory('InstAdmInfo', function ($resource, DateUtils) {
        return $resource('api/instAdmInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                isArray: false,
                transformResponse: function (data) {

                   /* if(!data){
                        data=angular.fromJson({id:0});
                    }else{
                        data = angular.fromJson(data);
                        console.log(data[0]);
                    }
                    return data[0];*/
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
    })
    .factory('InstAdmInfoDecline', function ($resource) {
        return $resource('api/instAdmInfos/decline/:id', {}, {
            'update': { method: 'PUT', isArray: true}
        });
    });
