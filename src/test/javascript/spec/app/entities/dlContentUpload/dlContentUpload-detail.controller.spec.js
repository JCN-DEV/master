'use strict';

describe('DlContentUpload Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockDlContentUpload, MockDlContentSetup, MockDlBookType;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockDlContentUpload = jasmine.createSpy('MockDlContentUpload');
        MockDlContentSetup = jasmine.createSpy('MockDlContentSetup');
        MockDlBookType = jasmine.createSpy('MockDlBookType');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'DlContentUpload': MockDlContentUpload,
            'DlContentSetup': MockDlContentSetup,
            'DlBookType': MockDlBookType
        };
        createController = function() {
            $injector.get('$controller')("DlContentUploadDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:dlContentUploadUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
