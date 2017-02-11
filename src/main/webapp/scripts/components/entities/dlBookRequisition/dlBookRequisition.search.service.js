'use strict';

angular.module('stepApp')
    .factory('DlBookRequisitionSearch', function ($resource) {
        return $resource('api/_search/dlBookRequisitions/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
