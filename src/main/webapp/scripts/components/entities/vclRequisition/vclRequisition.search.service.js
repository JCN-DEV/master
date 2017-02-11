'use strict';

angular.module('stepApp')
    .factory('VclRequisitionSearch', function ($resource) {
        return $resource('api/_search/vclRequisitions/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
