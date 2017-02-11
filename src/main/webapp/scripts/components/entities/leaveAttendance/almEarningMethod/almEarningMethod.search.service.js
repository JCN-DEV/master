'use strict';

angular.module('stepApp')
    .factory('AlmEarningMethodSearch', function ($resource) {
        return $resource('api/_search/almEarningMethods/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
