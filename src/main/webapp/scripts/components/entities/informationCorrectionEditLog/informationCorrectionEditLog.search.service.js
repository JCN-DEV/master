'use strict';

angular.module('stepApp')
    .factory('InformationCorrectionEditLogSearch', function ($resource) {
        return $resource('api/_search/informationCorrectionEditLogs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
