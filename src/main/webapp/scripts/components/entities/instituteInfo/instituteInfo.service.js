'use strict';

angular.module('stepApp')
    .factory('InstituteInfo', function ($resource, DateUtils) {


        return $resource('api/instituteInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.publicationDate = DateUtils.convertLocaleDateFromServer(data.publicationDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.publicationDate = DateUtils.convertLocaleDateToServer(data.publicationDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {

                    data.publicationDate = DateUtils.convertLocaleDateToServer(data.publicationDate);
                    console.log(angular.toJson(data));
                    return angular.toJson(data);
                }
            }
        });
    });
