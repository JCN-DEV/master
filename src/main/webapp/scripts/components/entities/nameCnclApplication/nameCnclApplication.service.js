'use strict';

angular.module('stepApp')
    .factory('NameCnclApplication', function ($resource, DateUtils) {
        return $resource('api/nameCnclApplications/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.created_date = DateUtils.convertLocaleDateFromServer(data.created_date);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    if(data && data.created_date){
                        data.created_date = DateUtils.convertLocaleDateToServer(data.created_date);
                    }
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.created_date = DateUtils.convertLocaleDateToServer(data.created_date);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('NameCnclApplicationCheck', function ($resource) {
        return $resource('api/nameCnclApplications/instEmployee/:code', {}, {
            'get':{
                method: 'GET',
                transformResponse: function (data) {
                    if(!data){
                        data={id:0};
                    }
                    data = angular.fromJson(data);

                    return data;
                }
            }
            /*'get': { method: 'GET', isArray: false}*/
        });
    });
