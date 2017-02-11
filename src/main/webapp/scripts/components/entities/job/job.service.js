'use strict';

angular.module('stepApp')
    .factory('Job', function ($resource, DateUtils) {
        return $resource('api/jobs/:id', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if(data){
                        data = angular.fromJson(data);
                        data.publishedAt = DateUtils.convertLocaleDateFromServer(data.publishedAt);
                        data.applicationDeadline = DateUtils.convertLocaleDateFromServer(data.applicationDeadline);
                    }

                    return data;
                }
            },
            'update': {
                method: 'PUT'
            }

        });
    });
