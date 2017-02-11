'use strict';

angular.module('stepApp')
    .factory('DlContentUploadSearch', function ($resource) {
        return $resource('api/_search/dlContentUploads/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
