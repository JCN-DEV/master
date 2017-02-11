'use strict';

angular.module('stepApp')
    .factory('OrganizationCategory', function ($resource, DateUtils) {
        return $resource('api/organizationCategorys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data)
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
