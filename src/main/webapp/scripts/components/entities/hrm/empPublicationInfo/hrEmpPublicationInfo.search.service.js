'use strict';

angular.module('stepApp')
    .factory('HrEmpPublicationInfoSearch', function ($resource) {
        return $resource('api/_search/hrEmpPublicationInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
