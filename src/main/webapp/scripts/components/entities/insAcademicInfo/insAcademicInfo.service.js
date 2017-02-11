'use strict';

angular.module('stepApp')
    .factory('InsAcademicInfo', function ($resource,DateUtils) {
        return $resource('api/insAcademicInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {

                   /* if(!data){
                        data=angular.fromJson({id:0});
                    }else{
                        data = angular.fromJson(data);
                        console.log(data);
                    }*/
                    //return data;
                   if(!data){
                       data={id:0};
                   }
                   return data = angular.fromJson(data);
                }
            },
            'update': { method:'PUT' }
        });
    })
    .factory('InstituteAcademicInfoDecline', function ($resource,DateUtils) {
        return $resource('api/insAcademicInfos/decline/:id', {}, {
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    return angular.toJson(data);
                }
            }
        });
    });
