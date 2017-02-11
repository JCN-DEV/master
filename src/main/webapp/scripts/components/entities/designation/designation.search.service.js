'use strict';

angular.module('stepApp')
    .factory('DesignationSearch', function ($resource) {
        return $resource('api/_search/designations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
