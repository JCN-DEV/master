'use strict';

describe('QrcodeGenLog Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockQrcodeGenLog;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockQrcodeGenLog = jasmine.createSpy('MockQrcodeGenLog');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'QrcodeGenLog': MockQrcodeGenLog
        };
        createController = function() {
            $injector.get('$controller')("QrcodeGenLogDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:qrcodeGenLogUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
