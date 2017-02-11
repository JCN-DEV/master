'use strict';

describe('InformationCorrectionEditLog Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInformationCorrectionEditLog, MockInformationCorrection;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInformationCorrectionEditLog = jasmine.createSpy('MockInformationCorrectionEditLog');
        MockInformationCorrection = jasmine.createSpy('MockInformationCorrection');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InformationCorrectionEditLog': MockInformationCorrectionEditLog,
            'InformationCorrection': MockInformationCorrection
        };
        createController = function() {
            $injector.get('$controller')("InformationCorrectionEditLogDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:informationCorrectionEditLogUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
