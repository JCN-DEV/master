'use strict';

angular.module('stepApp')
    .factory('HrEmpPromotionInfoSearch', function ($resource) {
        return $resource('api/_search/hrEmpPromotionInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
