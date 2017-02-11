'use strict';

angular.module('stepApp')
    .factory('JpEmployee', function ($resource, DateUtils) {
        return $resource('api/jpEmployees/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if(data){
                        data = angular.fromJson(data);
                        data.dob = DateUtils.convertLocaleDateFromServer(data.dob);
                    }

                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dob = DateUtils.convertLocaleDateToServer(data.dob);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dob = DateUtils.convertLocaleDateToServer(data.dob);
                    return angular.toJson(data);
                }
            }
        });
    });
